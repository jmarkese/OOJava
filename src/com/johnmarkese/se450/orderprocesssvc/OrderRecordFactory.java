package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderRecordFactory {

	private OrderRecordFactory() {}
	
	public static OrderRecord orderRecord(String source, String order, String itemCode, int numItems, int numDaysProcess,
			int numDaysTravel, LocalDate dayStart) throws ParameterValidationException, OrderProcessorException {
		if (source == null || order == null || dayStart == null) {
			throw new ParameterValidationException("None of the values source, orderId, and dayStart can be null");
		} else if (numItems < 1) {
			throw new ParameterValidationException(
					"The value numItems cannot be less than 1");
		} else if (numDaysProcess < 0 || numDaysTravel < 0){
			throw new ParameterValidationException("The value numDaysTravel and numDaysProcess cannot be less than 0");		
		}
		return new OrderRecordImpl(source, order, itemCode, numItems, numDaysProcess, numDaysTravel, dayStart);
	}
	
	public static OrderRecordCommitted orderRecordCommit(OrderRecord record, int numItems) throws ParameterValidationException, OrderProcessorException{
		if(record == null){
			throw new ParameterValidationException("The record to commit cannot be null");
		}
		return new OrderRecordCommittedImpl(record, numItems);
	}

	public static OrderRecordCommitted orderRecordBackorder(OrderDTO order, String itemCode, int numItems, LocalDate dayStart) throws ParameterValidationException, OrderProcessorException {
		if (order == null || itemCode == null || dayStart == null) {
			throw new ParameterValidationException("None of the values itemCode, orderId, and dayStart can be null");
		} else if (numItems < 1) {
			throw new ParameterValidationException("The value numItems cannot be less than 1");
		}
		OrderRecordNoImpl norecord = new OrderRecordNoImpl(order, itemCode, dayStart);
		return new OrderRecordCommittedBackorder(norecord, numItems);
	}
}