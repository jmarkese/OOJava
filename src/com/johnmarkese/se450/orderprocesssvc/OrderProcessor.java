package com.johnmarkese.se450.orderprocesssvc;

import java.util.ArrayList;
import java.util.HashMap;

import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public interface OrderProcessor {
	//public HashMap<ItemDTO, ArrayList<OrderRecordCommitted>> processOrder(OrderDTO order) throws OrderProcessorException;
	//public ProcessedOrderDTO processOrder(OrderDTO order) throws OrderProcessorException, ParameterValidationException;

	public OrderProcessedDTO processedOrderDTO() throws ParameterValidationException, OrderProcessorException;
}
