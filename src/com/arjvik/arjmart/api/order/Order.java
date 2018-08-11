package com.arjvik.arjmart.api.order;

public class Order {
	private int orderID;
	private int userID;
	private String status;
	
	public Order() {
		
	}
	public Order(int orderID, int userID, String status) {
		this();
		this.orderID = orderID;
		this.userID = userID;
		this.status = status;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
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
		result = prime * result + orderID;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + userID;
		return result;
	}
	@Override
	public String toString() {
		return "Order [orderID=" + orderID + ", userID=" + userID + ", status=" + status + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (orderID != other.orderID)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (userID != other.userID)
			return false;
		return true;
	}
}
