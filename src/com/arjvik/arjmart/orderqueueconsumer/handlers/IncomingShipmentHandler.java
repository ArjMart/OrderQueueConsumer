package com.arjvik.arjmart.orderqueueconsumer.handlers;

import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.INVENTORY;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ORDER;
import static com.arjvik.arjmart.orderqueueconsumer.schema.Tables.ORDERLINE;
import static org.jooq.impl.DSL.param;

import java.io.IOException;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.jooq.DSLContext;
import org.jooq.Param;

import com.arjvik.arjmart.api.location.Inventory;
import com.arjvik.arjmart.api.order.OrderLine;
import com.arjvik.arjmart.orderqueueconsumer.MessageQueueHandler;
import com.arjvik.arjmart.orderqueueconsumer.jms.JMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@MessageQueueHandler("arjmart.IncomingShipment")
@Slf4j
public class IncomingShipmentHandler implements MessageListener {

	private DSLContext database;
	private JMSService jmsservice;
	private ObjectMapper mapper;
	
	@Inject
	public IncomingShipmentHandler(JMSService jmsservice, DSLContext database, ObjectMapper mapper) throws JMSException {
		this.jmsservice = jmsservice;
		this.database = database;
		this.mapper = mapper;
	}
	
	@Override
	@SneakyThrows({JsonProcessingException.class, JMSException.class, IOException.class})
	public void onMessage(Message message){
		TextMessage textMessage = (TextMessage) message;
		Inventory incomingShipment = mapper.readValue(textMessage.getText(), Inventory.class);
		handleShipment(incomingShipment);
	}

	private void handleShipment(Inventory incomingShipment) {
		log.info("Incoming Shipment: {}", incomingShipment);
		List<OrderLine> orderLines;
		while(!(orderLines = getOrderLineToFulfill(incomingShipment)).isEmpty()) {
			OrderLine orderLine = orderLines.get(0);
			log.info("Queueing OrderLine Fulfillment: {}", orderLine);
			fulfillOrderLine(incomingShipment, orderLine);
		}
				
	}

	private void fulfillOrderLine(Inventory incomingShipment, OrderLine orderLine) {
		//SHOULD BE DONE THROUGH REST API, BUT ITS FINE
		database.update(ORDERLINE)
				.set(ORDERLINE.STATUS, "Fulfilled")
				.where(ORDERLINE.ORDERID.eq(orderLine.getOrderID()))
				.and(ORDERLINE.ORDERLINEID.eq(orderLine.getOrderLineID()))
				.execute();
		database.update(INVENTORY)
				.set(INVENTORY.INVENTORYAMOUNT, INVENTORY.INVENTORYAMOUNT.minus(orderLine.getQuantity()))
				.where(INVENTORY.LOCATIONID.eq(incomingShipment.getLocationID()))
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

	@SneakyThrows({JsonProcessingException.class, JMSException.class})
	private void runOrderLineFulfillmentPipeline(OrderLine orderLine) {
		jmsservice.sendJsonMessage("arjmart.OrderLineFulfillment", orderLine);
	}

	private List<OrderLine> getOrderLineToFulfill(Inventory incomingShipment) {
		Param<Integer> SKU = param("SKU", incomingShipment.getSKU());
		Param<Integer> ItemAttributeID = param("ItemAttributeID", incomingShipment.getItemAttributeID());
		Param<Integer> LocationID = param("LocationID", incomingShipment.getLocationID());
		return  database.select(ORDERLINE.ORDERID,
								ORDERLINE.ORDERLINEID,
								ORDERLINE.SKU,
								ORDERLINE.ITEMATTRIBUTEID,
								ORDERLINE.QUANTITY)
						.from(ORDER)
						.leftJoin(ORDERLINE)
						.on(ORDERLINE.ORDERID.eq(ORDER.ORDERID))
						.where(ORDERLINE.STATUS.eq("Pending"))
						.and(ORDER.ORDERSTATUS.eq("Pending"))
						.and(ORDERLINE.SKU.eq(SKU))
						.and(ORDERLINE.ITEMATTRIBUTEID.eq(ItemAttributeID))
						.and(ORDERLINE.QUANTITY.le(database.select(INVENTORY.INVENTORYAMOUNT)
														   .from(INVENTORY)
														   .where(INVENTORY.SKU.eq(SKU))
														   .and(INVENTORY.ITEMATTRIBUTEID.eq(ItemAttributeID))
														   .and(INVENTORY.LOCATIONID.eq(LocationID))))
						.orderBy(ORDERLINE.ORDERID.asc(), ORDERLINE.ORDERLINEID.asc())
						.limit(1)
						.fetch(record -> new OrderLine(record.get(ORDERLINE.ORDERID),
													   record.get(ORDERLINE.ORDERLINEID),
													   record.get(ORDERLINE.SKU),
													   record.get(ORDERLINE.ITEMATTRIBUTEID),
													   record.get(ORDERLINE.QUANTITY),
													   "Pending"));
	}

}
