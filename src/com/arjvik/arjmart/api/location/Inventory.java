package com.arjvik.arjmart.api.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory{
	private int locationID;
	private int SKU;
	private int itemAttributeID;
	private int quantity;
}
