package com.arjvik.arjmart.orderqueueconsumer.handlers;

import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ITEMATTRIBUTEMASTER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ITEMMASTER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ITEMPRICE;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ORDER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.USER;
import static org.jooq.impl.DSL.param;

import java.io.IOException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jooq.DSLContext;
import org.jooq.Param;

import com.arjvik.arjmart.api.order.OrderLine;
import com.arjvik.arjmart.orderqueueconsumer.MessageQueueHandler;
import com.arjvik.arjmart.orderqueueconsumer.email.EmailException;
import com.arjvik.arjmart.orderqueueconsumer.email.EmailService;
import com.arjvik.arjmart.orderqueueconsumer.email.templates.EmailTemplateStore;
import com.arjvik.arjmart.orderqueueconsumer.entities.SuperOrderLine;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.andreinc.aleph.AlephFormatter;
import net.andreinc.aleph.AlephFormatter.Styles;

@MessageQueueHandler("OrderLineFulfillment")
@Slf4j
public class OrderLineFulfillmentHandler implements MessageListener {
	
	private static String ORDER_LINE_FULFILLED_BODY = "OrderLineFulfilledBody";
	private static String ORDER_LINE_FULFILLED_SUBJECT = "OrderLineFulfilledSubject";
	
	private EmailService emailService;
	private EmailTemplateStore templateStore;
	private DSLContext database;
	private ObjectMapper mapper;

	@Inject
	public OrderLineFulfillmentHandler(EmailService emailService, EmailTemplateStore templateStore, DSLContext database, ObjectMapper mapper) {
		this.emailService = emailService;
		this.templateStore = templateStore;
		this.database = database;
		this.mapper = mapper;
	}
	
	@Override
	@SneakyThrows({JsonProcessingException.class, JMSException.class, IOException.class, EmailException.class})
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		OrderLine orderLine = mapper.readValue(textMessage.getText(), OrderLine.class);
		fulfillOrderLine(orderLine);
	}

	private void fulfillOrderLine(OrderLine orderLine) throws EmailException {
		log.info("Fulfilling OrderLine: {}", orderLine);
		SuperOrderLine superOrderLine = getSuperOrderLine(orderLine);
		log.info("SuperOrderLine: {}", superOrderLine);
		double totalPrice = orderLine.getQuantity() * superOrderLine.getPrice();
		String body = AlephFormatter.str(templateStore.getTemplate(ORDER_LINE_FULFILLED_BODY))
									.style(Styles.DOLLARS)
									.arg("o", superOrderLine)
									.arg("totalPrice", totalPrice)
									.fmt();
		String subj = AlephFormatter.str(templateStore.getTemplate(ORDER_LINE_FULFILLED_SUBJECT))
									.style(Styles.DOLLARS)
									.arg("o", superOrderLine)
									.arg("totalPrice", totalPrice)
									.fmt();
		String to = getEmailFromOrderID(orderLine.getOrderID());
		emailService.sendEmail(to, "noreply@mail.arjmart.tk", "ArjMart", subj, body);
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

}
