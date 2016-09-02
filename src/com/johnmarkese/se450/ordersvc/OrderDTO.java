package com.johnmarkese.se450.ordersvc;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

import com.johnmarkese.se450.itemsvc.ItemDTO;

public class OrderDTO {
	public String orderId;
	public LocalDate day;
	public String facility;
	public LinkedHashMap<ItemDTO, Integer> items;
	/**
	 * @param orderId
	 * @param day
	 * @param facility
	 * @param items
	 */
	public OrderDTO(String orderId, LocalDate day, String facility, Map<ItemDTO, Integer> items) {
		this.orderId = orderId;
		this.day = day;
		this.facility = facility;
		this.items = new LinkedHashMap<ItemDTO, Integer>(items);
	}
	
	public String toString (){
		return this.orderId + " " + this.day + " " + this.facility + " " + this.items.toString();
	}

}
