package com.arjvik.arjmart.orderqueueconsumer.entities;

import com.arjvik.arjmart.api.item.Item;
import com.arjvik.arjmart.api.item.ItemAttribute;
import com.arjvik.arjmart.api.item.ItemPrice;
import com.arjvik.arjmart.api.order.OrderLine;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SuperOrderLine extends OrderLine {
	private int orderID;
	private int orderLineID;
	private int SKU;
	private int itemAttributeID;
	private int quantity;
	private String status;
	private String itemName;
	private String itemDescription;
	private String itemThumbnail;
	private String itemAttributeColor;
	private String itemAttributeSize;
	private double price;

	public SuperOrderLine() {
		//Blank
	}
	public SuperOrderLine(OrderLine orderLine) {
		//OrderLine helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus());
	}
	public SuperOrderLine(OrderLine orderLine, Item item) {
		//OrderLine, Item helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus(), item.getName(), item.getDescription(), item.getThumbnail());
	}
	public SuperOrderLine(OrderLine orderLine, Item item, ItemAttribute itemAttribute) {
		//OrderLine, Item, ItemAttributeID helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus(), item.getName(), item.getDescription(), item.getThumbnail(), itemAttribute.getColor(), itemAttribute.getSize());
	}
	public SuperOrderLine(OrderLine orderLine, Item item, ItemAttribute itemAttribute, ItemPrice price) {
		//OrderLine, Item, ItemAttributeID, ItemPrice helper constructor
		this(orderLine.getOrderID(), orderLine.getOrderLineID(), orderLine.getSKU(), orderLine.getItemAttributeID(), orderLine.getQuantity(), orderLine.getStatus(), item.getName(), item.getDescription(), item.getThumbnail(), itemAttribute.getColor(), itemAttribute.getSize(), price.getPrice());
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status) {
		//OrderLine
		this(orderID,orderLineID,SKU,itemAttributeID,quantity,status,null,null,null);
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status, String itemName, String itemDescription, String itemThumbnail) {
		//OrderLine, Item
		this(orderID,orderLineID,SKU,itemAttributeID,quantity,status,itemName,itemDescription,itemThumbnail,null,null);
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status, String itemName, String itemDescription, String itemThumbnail, String itemAttributeColor, String itemAttributeSize) {
		//OrderLine, Item, ItemAttribute
		this(orderID,orderLineID,SKU,itemAttributeID,quantity,status,itemName,itemDescription,itemThumbnail,itemAttributeColor,itemAttributeSize,-1);
	}
	public SuperOrderLine(int orderID, int orderLineID, int SKU, int itemAttributeID, int quantity, String status, String itemName, String itemDescription, String itemThumbnail, String itemAttributeColor, String itemAttributeSize, double price) {
		//Master Constructor: OrderLine, Item, ItemAttribute, ItemPrice
		this();
		this.orderID = orderID;
		this.orderLineID = orderLineID;
		this.SKU = SKU;
		this.itemAttributeID = itemAttributeID;
		this.quantity = quantity;
		this.status = status;
		this.itemName = itemName;
		this.itemDescription = itemDescription;
		this.itemThumbnail = itemThumbnail;
		this.itemAttributeColor = itemAttributeColor;
		this.itemAttributeSize = itemAttributeSize;
		this.price = price;
	}
}
