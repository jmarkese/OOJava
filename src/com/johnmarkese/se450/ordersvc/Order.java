package com.johnmarkese.se450.ordersvc;

import java.time.LocalDate;
import java.util.Map;

public interface Order {
	public String getOrderId();
	public LocalDate getDay();
	public String getFacility();
	public Map getItems();
	public OrderDTO getDTO();
}
