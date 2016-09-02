package com.johnmarkese.se450.orderprocesssvc;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.facilitysvc.FacilityException;
import com.johnmarkese.se450.facilitysvc.FacilitySvc;
import com.johnmarkese.se450.facilitysvc.ScheduleException;
import com.johnmarkese.se450.inventorysvc.InventoryDepletedException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.networksvc.NetworkSvc;
import com.johnmarkese.se450.networksvc.NetworkSvc.Round;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.ordersvc.OrderException;
import com.johnmarkese.se450.ordersvc.OrderSvc;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderProcessorSvc {

	private volatile static OrderProcessorSvc instance;
	private static NetworkSvc ns;
	private static FacilitySvc fs;
	private static OrderSvc os;
	private ArrayList<OrderProcessor> orderProcessors;

	private OrderProcessorSvc() throws DataLoaderException, ParameterValidationException, OrderException, DOMException,
			ParserConfigurationException, SAXException, IOException, ShortestPathException, InventoryException, OrderProcessorException {
		this.ns = NetworkSvc.getInstance();
		this.fs = FacilitySvc.getInstance();
		this.os = OrderSvc.getInstance();
	}

	public static OrderProcessorSvc getInstance() throws OrderProcessorException {
		try {
			if (instance == null) {
				synchronized (OrderProcessorSvc.class) {
					if (instance == null) {
						instance = new OrderProcessorSvc();
					}
				}
			}
			return instance;
		} catch (ParameterValidationException | DataLoaderException
				| InventoryException | DOMException | OrderException | ParserConfigurationException | SAXException | IOException | ShortestPathException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}
	
	public ArrayList<OrderProcessedDTO> processedOrders() throws ParameterValidationException, OrderProcessorException, OrderException, DataLoaderException {
		this.setOrderProcessors();
		ArrayList<OrderProcessedDTO> processedOrders = new ArrayList<OrderProcessedDTO>();
		for(OrderProcessor o : orderProcessors){
			processedOrders.add(o.processedOrderDTO());
		}
		return processedOrders;
	}

	private void setOrderProcessors() throws OrderProcessorException, ParameterValidationException, OrderException, DataLoaderException {
		this.orderProcessors = new ArrayList<OrderProcessor>();
		for(OrderDTO o : os.getOrders()){
			orderProcessors.add(OrderProcessorFactory.orderProcessor(o.orderId));
		}
	}

	//==================================================================//
	//
	//		Package level functions to interface with other modules
	//
	//==================================================================//
	
	void bookFacilty(String facility, int numUnits, String itemCode, LocalDate day)
			throws OrderProcessorException {
		try {
			fs.bookFacilty(facility, numUnits, itemCode, day);
		} catch (ScheduleException | InventoryDepletedException | ParameterValidationException | DataLoaderException
				| InventoryException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}

	double getTransportCost(int numDaysTravel, String from, String to) {
		//if you needed more sophisticated transportation calculations you would get that here.
		double transFee = ns.getTransportationFee();
		return transFee * numDaysTravel;
	}

	int getDistanceModified(String facility1, String facility2)  throws OrderProcessorException {
		try {
			ArrayList<String> list = new ArrayList(ns.shortestPath(facility1, facility2));
			//System.out.println(ns.getDistance() +" "+ list.toString() +" "+ ns.getDistanceModified(Round.UP));
			return (int) ns.getDistanceModified(Round.UP);
		} catch (ParameterValidationException | ShortestPathException | DOMException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}

	LocalDate getEarliestCompleteDate(String name, LocalDate day, int itemsProcess)  throws OrderProcessorException {
		try {
			return fs.getEarliestCompleteDate(name, day, itemsProcess);
		} catch (FacilityException | ScheduleException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}

	HashMap<FacilityDTO, Integer> findFacilityItem(String item) throws OrderProcessorException {
		try {
			return fs.findFacilityItem(item);
		} catch (FacilityException | ScheduleException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}

	FacilityDTO getFacility(String facility) throws OrderProcessorException {
		try {
			return fs.getFacility(facility);
		} catch (FacilityException | ScheduleException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}

	OrderDTO getOrder(String order) {
		return os.getOrder(order);
	}

	ItemDTO getItem(String code) throws DataLoaderException {
		return os.getItemDTO(code);
	}

}
