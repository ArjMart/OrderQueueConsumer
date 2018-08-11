package com.arjvik.arjmart.orderqueueconsumer.handlers;

import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.INVENTORY;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ITEMATTRIBUTEMASTER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ITEMMASTER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ITEMPRICE;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ORDER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ORDERLINE;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.USER;
import static org.jooq.impl.DSL.param;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jooq.DSLContext;
import org.jooq.Param;

import com.arjvik.arjmart.api.order.Order;
import com.arjvik.arjmart.api.order.OrderLine;
import com.arjvik.arjmart.orderqueueconsumer.MessageQueueHandler;
import com.arjvik.arjmart.orderqueueconsumer.email.EmailException;
import com.arjvik.arjmart.orderqueueconsumer.email.EmailService;
import com.arjvik.arjmart.orderqueueconsumer.email.templates.EmailTemplateStore;
import com.arjvik.arjmart.orderqueueconsumer.entities.SuperOrderLine;
import com.arjvik.arjmart.orderqueueconsumer.jms.JMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.aleph.AlephFormatter;
import net.andreinc.aleph.AlephFormatter.Styles;

@MessageQueueHandler("OrderPlaced")
@Slf4j
public class OrderPlacedHandler implements MessageListener {
	
	private static final String ORDER_PLACED_BODY = "OrderPlacedBody";
	private static final String ORDER_PLACED_ROWS = "OrderPlacedRows";
	private static final String ORDER_PLACED_SUBJECT = "OrderPlacedSubject";

	private static final NumberFormat priceFormat = NumberFormat.getCurrencyInstance(Locale.US);

	private EmailService emailService;
	private EmailTemplateStore templateStore;
	private DSLContext database;
	private JMSService jmsservice;
	private ObjectMapper mapper;

	@Inject
	public OrderPlacedHandler(EmailService emailService, EmailTemplateStore templateStore, DSLContext database, JMSService jmsservice, ObjectMapper mapper) {
		this.emailService = emailService;
		this.templateStore = templateStore;
		this.database = database;
		this.jmsservice = jmsservice;
		this.mapper = mapper;
	}
	
	@Override
	@SneakyThrows({JsonProcessingException.class, JMSException.class, IOException.class, EmailException.class})
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		Order order = mapper.readValue(textMessage.getText(), Order.class);
		fulfillOrder(order);
	}

	private void fulfillOrder(Order order) throws EmailException {
		log.info("Fulfilling Order: {}", order);
		List<OrderLine> orderLines = getOrderLines(order.getOrderID());
		sendEmail(order, orderLines);
		checkForFulfillableOrderLines(orderLines);
	}

	private List<OrderLine> getOrderLines(int orderID) {
		return
			database.select(ORDERLINE.ORDERLINEID,
							ORDERLINE.SKU,
							ORDERLINE.ITEMATTRIBUTEID,
							ORDERLINE.QUANTITY,
							ORDERLINE.STATUS)
					.from(ORDERLINE)
					.where(ORDERLINE.ORDERID.eq(param("OrderID", orderID)))
					.fetch(record -> new OrderLine(orderID,
													record.get(ORDERLINE.ORDERLINEID),
													record.get(ORDERLINE.SKU),
													record.get(ORDERLINE.ITEMATTRIBUTEID),
													record.get(ORDERLINE.QUANTITY),
													record.get(ORDERLINE.STATUS)));
	}

	@SneakyThrows(EmailException.class)
	private void sendEmail(Order order, List<OrderLine> orderLines) {
		StringBuilder orderLineRows = new StringBuilder();
		double total = 0;
		for(OrderLine orderLine : orderLines) {
			SuperOrderLine superOrderLine = getSuperOrderLine(orderLine);
			double totalRowPrice = superOrderLine.getPrice() * superOrderLine.getQuantity();
			total += totalRowPrice;
			String line = AlephFormatter.str(templateStore.getTemplate(ORDER_PLACED_ROWS))
										.style(Styles.DOLLARS)
										.arg("o", superOrderLine)
										.arg("unitPrice", formatAsPrice(superOrderLine.getPrice()))
										.arg("totalRowPrice", formatAsPrice(totalRowPrice))
										.fmt();
			orderLineRows.append(line);
		}
		String body = AlephFormatter.str(templateStore.getTemplate(ORDER_PLACED_BODY))
									.style(Styles.DOLLARS)
									.arg("o", order)
									.arg("orderLineRows", orderLineRows.toString())
									.arg("total", formatAsPrice(total))
									.fmt();
		String subj = AlephFormatter.str(templateStore.getTemplate(ORDER_PLACED_SUBJECT))
									.style(Styles.DOLLARS)
									.arg("o", order)
									.fmt();
		String to = getEmailFromOrderID(order.getOrderID());
		emailService.sendEmail(to, "noreply@mail.arjmart.tk", "ArjMart", subj, body);
	}
	
	private String formatAsPrice(double d) {
		return priceFormat.format(d);
	}
	
	private String getEmailFromOrderID(int orderID) {
		Param<Integer> orderIDParam = param("OrderID", orderID);
		return
			database.select(USER.EMAIL)
					.from(USER)
					.join(ORDER)
					.on(USER.USERID.eq(ORDER.USERID))
					.where(ORDER.ORDERID.eq(orderIDParam))
					.fetch(USER.EMAIL)
					.get(0);
	}
	
	private void checkForFulfillableOrderLines(List<OrderLine> orderLines) {
		orderLines.stream()
				  .parallel()
				  .map(orderLine -> database.select(INVENTORY.LOCATIONID)
											.from(INVENTORY)
											.where(INVENTORY.SKU.eq(param("SKU", orderLine.getSKU())))
											.and(INVENTORY.ITEMATTRIBUTEID.eq(param("ItemAttributeID", orderLine.getItemAttributeID())))
											.and(INVENTORY.INVENTORYAMOUNT.ge(param("Quantity", orderLine.getQuantity())))
											.limit(1)
											.fetchOptional(record -> new Pair<>(record.get(INVENTORY.LOCATIONID), orderLine)))
				  .filter(Optional::isPresent)
				  .map(Optional::get)
				  .forEach(pair -> fulfillOrderLine(pair.getValue1(), pair.getValue2()));
	}
	
	private void fulfillOrderLine(int locationID, OrderLine orderLine) {
		//SHOULD BE DONE THROUGH REST API, BUT ITS FINE
		database.update(ORDERLINE)
				.set(ORDERLINE.STATUS, "Fulfilled")
				.where(ORDERLINE.ORDERID.eq(orderLine.getOrderID()))
				.and(ORDERLINE.ORDERLINEID.eq(orderLine.getOrderLineID()))
				.execute();
		database.update(INVENTORY)
				.set(INVENTORY.INVENTORYAMOUNT, INVENTORY.INVENTORYAMOUNT.minus(orderLine.getQuantity()))
				.where(INVENTORY.LOCATIONID.eq(locationID))
				.and(INVENTORY.SKU.eq(orderLine.getSKU()))
				.and(INVENTORY.ITEMATTRIBUTEID.eq(orderLine.getItemAttributeID()))
				.execute();
		int pendingCount = 
				database.selectCount()
						.from(ORDERLINE)
						.where(ORDERLINE.ORDERID.eq(orderLine.getOrderID()))
						.and(ORDERLINE.STATUS.eq("Pending"))
						.fetch(record -> record.get(0, Integer.class))
						.get(0);
		if(pendingCount == 0) {
			database.update(ORDER)
					.set(ORDER.ORDERSTATUS, "Fulfilled")
					.where(ORDER.ORDERID.eq(orderLine.getOrderID()))
					.execute();
		}
		orderLine.setStatus("Fulfilled");
		runOrderLineFulfillmentPipeline(orderLine);
	}
	
	private SuperOrderLine getSuperOrderLine(OrderLine orderLine) {
		Param<Integer> SKU = param("SKU", orderLine.getSKU());
		Param<Integer> ItemAttributeID = param("ItemAttributeID", orderLine.getItemAttributeID());
		return
			database.select(ITEMATTRIBUTEMASTER.SKU,
							ITEMATTRIBUTEMASTER.ITEMATTRIBUTEID,
							ITEMATTRIBUTEMASTER.COLOR,
							ITEMATTRIBUTEMASTER.SIZE,
							ITEMMASTER.ITEMNAME,
							ITEMMASTER.ITEMDESCRIPTION,
							ITEMMASTER.ITEMTHUMBNAILS,
							ITEMPRICE.PRICE)
					.from(ITEMATTRIBUTEMASTER.join(ITEMMASTER)
												.on(ITEMATTRIBUTEMASTER.SKU.eq(ITEMMASTER.SKU))
											 .join(ITEMPRICE)
												.on(ITEMATTRIBUTEMASTER.SKU.eq(ITEMPRICE.SKU))
												.and(ITEMATTRIBUTEMASTER.ITEMATTRIBUTEID.eq(ITEMPRICE.ITEMATTRIBUTEID)))
					.where(ITEMATTRIBUTEMASTER.SKU.eq(SKU))
					.and(ITEMATTRIBUTEMASTER.ITEMATTRIBUTEID.eq(ItemAttributeID))
					.fetch(record -> new SuperOrderLine(orderLine.getOrderID(),
														orderLine.getOrderLineID(),
														orderLine.getSKU(),
														orderLine.getItemAttributeID(),
														orderLine.getQuantity(),
														orderLine.getStatus(),
														record.get(ITEMMASTER.ITEMNAME),
														record.get(ITEMMASTER.ITEMDESCRIPTION),
														record.get(ITEMMASTER.ITEMTHUMBNAILS),
														record.get(ITEMATTRIBUTEMASTER.COLOR),
														record.get(ITEMATTRIBUTEMASTER.SIZE),
														record.get(ITEMPRICE.PRICE)))
					.get(0);
	}
	
	@SneakyThrows({JsonProcessingException.class, JMSException.class})
	private void runOrderLineFulfillmentPipeline(OrderLine orderLine) {
		jmsservice.sendJsonMessage("OrderLineFulfillment", orderLine);
	}
	
	@Data
	private static class Pair<T1, T2> {
		private final T1 value1;
		private final T2 value2;
	}

}
