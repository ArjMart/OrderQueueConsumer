package com.arjvik.arjmart.api.order;

public class OrderLine {
	private int orderID;
	private int orderLineID;
	private int SKU;
	private int itemAttributeID;
	private int quantity;
	private String status;

	public OrderLine() {
		
	}
	public OrderLine(final int orderID, final int orderLineID, final int SKU, final int itemAttributeID, final int quantity, final String status) {
		this();
		this.orderID = orderID;
		this.orderLineID = orderLineID;
		this.SKU = SKU;
		this.itemAttributeID = itemAttributeID;
		this.quantity = quantity;
		this.status = status;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getOrderLineID() {
		return orderLineID;
	}
	public void setOrderLineID(int orderLineID) {
		this.orderLineID = orderLineID;
	}
	public int getSKU() {
		return SKU;
	}
	public void setSKU(int SKU) {
		this.SKU = SKU;
	}
	public int getItemAttributeID() {
		return itemAttributeID;
	}
	public void setItemAttributeID(int itemAttributeID) {
		this.itemAttributeID = itemAttributeID;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SKU;
		result = prime * result + itemAttributeID;
		result = prime * result + orderID;
		result = prime * result + orderLineID;
		result = prime * result + quantity;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "OrderLine [orderID=" + orderID + ", orderLineID=" + orderLineID + ", SKU=" + SKU
				+ ", itemAttributeID=" + itemAttributeID + ", quantity=" + quantity + ", status=" + status + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		if (SKU != other.SKU)
			return false;
		if (itemAttributeID != other.itemAttributeID)
			return false;
		if (orderID != other.orderID)
			return false;
		if (orderLineID != other.orderLineID)
			return false;
		if (quantity != other.quantity)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
}