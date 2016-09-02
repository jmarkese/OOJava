package com.johnmarkese.se450.ordersvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilitySvc;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.itemsvc.ItemSvc;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderImpl implements Order {
	private String orderId;
	private LocalDate day;
	private String facility;
	private LinkedHashMap<ItemDTO, Integer> items;

	public OrderImpl(String orderId, LocalDate day, String facility, HashMap<String, Integer> items2)
			throws ParameterValidationException, OrderException, DataLoaderException {
		super();
		this.setOrderId(orderId);
		this.setDay(day);
		this.setFacility(facility);
		this.setItems(items2);
	}

	@Override
	public String getOrderId() {
		return this.orderId;
	}

	@Override
	public LocalDate getDay() {
		return this.day; // LocalDate is immutable
	}

	@Override
	public String getFacility() {
		return this.facility;
	}

	@Override
	public Map getItems() {
		return (LinkedHashMap<String, Integer>) items.clone();
	}

	@Override
	public OrderDTO getDTO() {
		return new OrderDTO(this.getOrderId(), this.getDay(), this.getFacility(), this.getItems());
	}

	@Override
	public String toString() {
		return this.orderId + " to:" + this.facility + " on:" + day.toString();

	}

	private void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	private void setDay(LocalDate day) throws ParameterValidationException {
		if (day == null || day.isBefore(LocalDate.now())) {
			throw new ParameterValidationException("The day specified must be initilized and cannot be in the past");
		}
		this.day = day;
	}

	private void setFacility(String facility) throws ParameterValidationException, OrderException {
		if (facility == null || facility.isEmpty()) {
			throw new ParameterValidationException("There must be a facility set in the order.");
		}
		try {
			if (!FacilitySvc.getInstance().facilityExists(facility)) {
				throw new ParameterValidationException("The facilty does not exist.");
			}
		} catch (DOMException | DataLoaderException | InventoryException | ParserConfigurationException | SAXException
				| IOException | ShortestPathException e) {
			e.printStackTrace();
			throw new OrderException(e.getMessage());
		}
		this.facility = facility;
	}

	private void setItems(Map<String, Integer> items) throws ParameterValidationException, DataLoaderException {
		if (items == null || items.isEmpty()) {
			throw new ParameterValidationException("There must be items set in the order.");
		}
		LinkedHashMap<ItemDTO, Integer> itemDTOs = new LinkedHashMap<ItemDTO, Integer>();
		for (String i : items.keySet()) {
			if (!ItemSvc.getInstance().itemExists(i)) {
				throw new ParameterValidationException("The item " + i + " does not exist");
			} else {
				itemDTOs.put(ItemSvc.getInstance().getItem(i), items.get(i));
			}
		}
		this.items = itemDTOs;
	}
}
