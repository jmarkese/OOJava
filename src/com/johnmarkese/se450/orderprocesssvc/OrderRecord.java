package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;

import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public interface OrderRecord {

	public FacilityDTO getSource() throws OrderProcessorException;
	public OrderDTO getOrderDTO() throws OrderProcessorException;
	public String getItemCode();
	public int getNumItems();
	public int getNumDaysProcess();
	public int getNumDaysTravel();
	public LocalDate getDeliveryDay();
	public LocalDate getDayStart();
}
