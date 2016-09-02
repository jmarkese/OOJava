package com.johnmarkese.se450.ordersvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.facilitysvc.FacilityException;
import com.johnmarkese.se450.facilitysvc.FacilitySvc;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.inventorysvc.InventorySvc;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.itemsvc.ItemSvc;
import com.johnmarkese.se450.networksvc.NetworkSvc;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderSvc {
	private volatile static OrderSvc instance;
	private HashMap<String, Order> ordersMap;
	private ArrayList<Order> ordersList;
	

	private OrderSvc() throws DataLoaderException, ParameterValidationException, OrderException {
		loadOrders();
	}

	public static OrderSvc getInstance() throws OrderException, DataLoaderException, ParameterValidationException {
		if (instance == null) {
			synchronized (OrderSvc.class) {
				if (instance == null) {
					instance = new OrderSvc();
				}
			}
		}
		return instance;
	}
	
	public OrderDTO getOrder(String orderId){
		return ordersMap.get(orderId).getDTO();
	}
	
	public List<OrderDTO> getOrders(){
		ArrayList<OrderDTO> list = new ArrayList<OrderDTO>();
		for(Order o : this.ordersList){
			list.add(o.getDTO());
		}
		return list;
	}
	
	private void loadOrders() throws DataLoaderException, ParameterValidationException, OrderException {
		OrderLoader ol = new OrderLoaderFactory().dataLoader();
		this.ordersList = new ArrayList<Order>(ol.load());
		this.ordersMap = new HashMap<String, Order>();
		for (Order o : ordersList) {
			ordersMap.put(o.getOrderId(), o);
		}
	}

	public static ItemDTO getItemDTO(String code) throws DataLoaderException {
		return ItemSvc.getInstance().getItem(code);
	}
}
