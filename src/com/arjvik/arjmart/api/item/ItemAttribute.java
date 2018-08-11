package com.arjvik.arjmart.api.item;

public class ItemAttribute {
	private int SKU;
	private int ID;
	private String color;
	private String size;
	public ItemAttribute() {
		
	}
	public ItemAttribute(int SKU, int ID, String color, String size) {
		this();
		this.SKU = SKU;
		this.ID = ID;
		this.color = color;
		this.size = size;
	}
	public int getSKU() {
		return SKU;
	}
	public void setSKU(int SKU) {
		this.SKU = SKU;
	}
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ID;
		result = prime * result + SKU;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + ((size == null) ? 0 : size.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "ItemAttribute [ID=" + ID + ", SKU=" + SKU + ", color=" + color + ", size=" + size + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ItemAttribute other = (ItemAttribute) obj;
		if (ID != other.ID)
			return false;
		if (SKU != other.SKU)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		return true;
	}
}
