package com.johnmarkese.se450.inventorysvc;

public class InventoryDepletedException extends Exception {
	public InventoryDepletedException(String msg) {
		super(msg);
	}
}
