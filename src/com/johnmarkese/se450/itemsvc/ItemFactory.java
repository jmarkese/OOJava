package com.johnmarkese.se450.itemsvc;


import com.johnmarkese.se450.utils.ParameterValidationException;

public class ItemFactory {
	private ItemFactory(){}
	
	public static Item item(String code, double price) throws ParameterValidationException {
		if (code.isEmpty()) {
			throw new ParameterValidationException("Item code must be set");
		} else if (!Double.isFinite(price) || price < 0){
			throw new ParameterValidationException("Item price must be a non-negative finite number");	
		}
		return new ItemImpl(code, price);
	}
	
	public static Item item(String code, double price, String type) throws ParameterValidationException, ItemException {
		if(type.equals("null"))
			return new ItemNullImpl(code, price);
		else
			throw new ItemException ("Not yet implemented");
	}
}