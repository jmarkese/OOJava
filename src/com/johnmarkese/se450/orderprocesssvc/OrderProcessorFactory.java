package com.johnmarkese.se450.orderprocesssvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.ordersvc.OrderException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderProcessorFactory {
	private OrderProcessorFactory() {}
	public static OrderProcessor orderProcessor(String order) throws OrderProcessorException, ParameterValidationException {
		if (order == null || order.isEmpty()) {
			throw new ParameterValidationException("The value of orderId cannot be null or an empty string.");
		}
		return new OrderProcessorImpl(order);
	}
}
