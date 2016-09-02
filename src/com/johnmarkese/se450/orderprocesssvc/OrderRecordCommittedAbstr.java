package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public abstract class OrderRecordCommittedAbstr implements OrderRecordCommitted {
	private OrderRecord orderRecord;
	
	public OrderRecordCommittedAbstr(OrderRecord orderRecordIn) throws ParameterValidationException{
		if(orderRecordIn == null){
			throw new ParameterValidationException("The record to commit cannot be null");
		}
		this.orderRecord = orderRecordIn;
	}

	@Override
	public FacilityDTO getSource() throws OrderProcessorException {
		return orderRecord.getSource();
	}

	@Override
	public OrderDTO getOrderDTO() throws OrderProcessorException {
		return orderRecord.getOrderDTO();
	}

	@Override
	public String getItemCode() {
		return orderRecord.getItemCode();
	}

	@Override
	public int getNumItems() {
		return orderRecord.getNumItems();
	}

	@Override
	public int getNumDaysProcess() {
		return orderRecord.getNumDaysProcess();
	}

	@Override
	public int getNumDaysTravel() {
		return orderRecord.getNumDaysTravel();
	}

	@Override
	public LocalDate getDeliveryDay() {
		return orderRecord.getDeliveryDay();
	}
	
	@Override
	public LocalDate getDayStart() {
		return orderRecord.getDayStart();
	}
	
	@Override
	public String toString(){
		return orderRecord.toString();
	}

}
