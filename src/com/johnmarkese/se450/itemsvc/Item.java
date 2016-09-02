package com.johnmarkese.se450.itemsvc;

public interface Item extends ItemCode{
	
	public String getCode();

	public ItemDTO getDTO();
	
	public double getPrice();

}
