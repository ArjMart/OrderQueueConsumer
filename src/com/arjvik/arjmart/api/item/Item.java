package com.arjvik.arjmart.api.item;

public class Item {
	private int SKU;
	private String name;
	private String description;
	private String thumbnail;
	
	public Item() {
		
	}
	public Item(int SKU, String name, String description, String thumbnail) {
		this();
		this.SKU = SKU;
		this.name = name;
		this.description = description;
		this.thumbnail = thumbnail;
	}
	public int getSKU() {
		return SKU;
	}
	public void setSKU(int SKU) {
		this.SKU = SKU;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + SKU;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((thumbnail == null) ? 0 : thumbnail.hashCode());
		return result;
	}
	@Override
	public String toString() {
		return "Item [SKU=" + SKU + ", name=" + name + ", description=" + description + ", thumbnail=" + thumbnail + "]";
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (SKU != other.SKU)
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (thumbnail == null) {
			if (other.thumbnail != null)
				return false;
		} else if (!thumbnail.equals(other.thumbnail))
			return false;
		return true;
	}
}
