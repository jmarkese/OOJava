package com.johnmarkese.se450.ordersvc;

import java.time.LocalDate;
import java.util.HashMap;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderFactory {
	
	private OrderFactory (){}

	public static Order order(String orderId, LocalDate day, String facility, HashMap<String, Integer> items) throws ParameterValidationException, OrderException, DataLoaderException {
		if(orderId == null || facility == null  || items == null  || orderId.isEmpty() || facility.isEmpty() || items.isEmpty()){
			throw new ParameterValidationException("The order id, day and items must be set."); 			
		}
		if(day == null || day.isBefore(LocalDate.now())){
			throw new ParameterValidationException("The day must be set and cannot be in the past."); 
		}
		return new OrderImpl(orderId, day, facility, items);
	}

}
