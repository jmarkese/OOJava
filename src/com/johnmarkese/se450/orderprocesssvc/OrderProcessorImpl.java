package com.johnmarkese.se450.orderprocesssvc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.facilitysvc.FacilityDTO;
import com.johnmarkese.se450.facilitysvc.FacilityException;
import com.johnmarkese.se450.facilitysvc.ScheduleException;
import com.johnmarkese.se450.inventorysvc.InventoryDepletedException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.itemsvc.ItemCodeComparator;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.ordersvc.OrderDTO;
import com.johnmarkese.se450.ordersvc.OrderException;
import com.johnmarkese.se450.ordersvc.OrderSvc;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class OrderProcessorImpl implements OrderProcessor{

	private OrderDTO order;
	private HashMap<ItemDTO, ArrayList<OrderRecord>> orderRecords = new HashMap<ItemDTO, ArrayList<OrderRecord>>();
	private HashMap<ItemDTO, ArrayList<OrderRecordCommitted>> orderCommitted = new HashMap<ItemDTO, ArrayList<OrderRecordCommitted>>();
	private OrderRecordFactory orFactory; 
	
	public OrderProcessorImpl(String orderId) throws ParameterValidationException, OrderProcessorException{
		this.setOrder(orderId);
		this.processOrder();
	}
	
	@Override
	public OrderProcessedDTO processedOrderDTO() throws ParameterValidationException, OrderProcessorException {
		return makeOrderProcessedDTO(this.orderCommitted);
	}

	private void setOrder(String orderId) throws ParameterValidationException, OrderProcessorException {
		if (orderId == null || orderId.isEmpty()){
			throw new ParameterValidationException("The incoming order id cannot be null or empty");
		}
		this.order = this.retrieveOrder(orderId);
	}
	
	private OrderDTO retrieveOrder(String orderId) throws OrderProcessorException{
		return OrderProcessorSvc.getInstance().getOrder(orderId);
	}

	private void processOrder() throws OrderProcessorException, ParameterValidationException {
		try {
			for (ItemDTO item : order.items.keySet()) {
				int itemQty = order.items.get(item);
				ArrayList<OrderRecord> records = findOrderRecordSolutions(item.code, itemQty, order);
				this.orderRecords.put(item, records);
				ArrayList<OrderRecordCommitted> committed = commitOrderRecords(records, item.code, itemQty, order);
				this.orderCommitted.put(item, committed);
			}
		} catch (ScheduleException | InventoryDepletedException | DataLoaderException | InventoryException
				| FacilityException | ShortestPathException e) {
			e.printStackTrace();
			throw new OrderProcessorException(e.getMessage());
		}
	}
	

	private ArrayList<OrderRecord> findOrderRecordSolutions(String item, int qty, OrderDTO order)
			throws FacilityException, ScheduleException, ParameterValidationException, ShortestPathException, OrderProcessorException {
		ArrayList<OrderRecord> solutions = new ArrayList<OrderRecord>();
		FacilityDTO myDTO = OrderProcessorSvc.getInstance().getFacility(order.facility);
		HashMap<FacilityDTO, Integer> fItems = OrderProcessorSvc.getInstance().findFacilityItem(item);
		fItems.remove(myDTO);
		//System.out.println(item + ":" + fItems);
		for (FacilityDTO fac : fItems.keySet()) {
			int avail = fItems.get(fac);
			solutions.add(findSolution(fac, order, avail, item, qty));
		}
		Collections.sort(solutions, new OrderRecordDeliveryComparator());
		//System.out.println(solutions);
		return solutions;
	}

	private OrderRecord findSolution(FacilityDTO fac, OrderDTO order, int avail, String item, int qty)
			throws ParameterValidationException, ShortestPathException, FacilityException, ScheduleException,
			OrderProcessorException {
		int itemsProcess = (avail <= qty) ? avail : qty;
		int numDaysTravel = OrderProcessorSvc.getInstance().getDistanceModified(fac.name, order.facility);
		int processDays = (int) ChronoUnit.DAYS.between(order.day,
				OrderProcessorSvc.getInstance().getEarliestCompleteDate(fac.name, order.day, itemsProcess));
		return orFactory.orderRecord(fac.name, order.orderId, item, itemsProcess, processDays, numDaysTravel,
				order.day);
	}

	private OrderRecord findSolution(OrderRecord record, int qty) throws ParameterValidationException,
			ShortestPathException, FacilityException, ScheduleException, OrderProcessorException {
		return findSolution(record.getSource(), record.getOrderDTO(), qty, record.getItemCode(), qty);
	}
	
	private ArrayList<OrderRecordCommitted> commitOrderRecords(ArrayList<OrderRecord> records, String item, int itemQty,
			OrderDTO order) throws ScheduleException, InventoryDepletedException, ParameterValidationException, DataLoaderException, InventoryException, OrderProcessorException, ShortestPathException, FacilityException {

		ArrayList<OrderRecordCommitted> bookings = new ArrayList<OrderRecordCommitted>();
		while (itemQty > 0 && !records.isEmpty()) {
			OrderRecord record = records.remove(0);
			int commitUnits = (itemQty >= record.getNumItems()) ? record.getNumItems() : itemQty;
			record = (itemQty < record.getNumItems()) ? findSolution(record, itemQty) : record;
			itemQty -= commitUnits;
			OrderRecordCommitted committed = orFactory.orderRecordCommit(record, commitUnits);
			bookings.add(committed);
		}
		
		if(itemQty > 0){
			OrderRecordCommitted record = orFactory.orderRecordBackorder(order, item, itemQty, order.day);
			bookings.add(record);
		}
		//System.out.println(bookings);
		return bookings;
	}
	
	private OrderProcessedDTO makeOrderProcessedDTO(HashMap<ItemDTO, ArrayList<OrderRecordCommitted>> items) throws ParameterValidationException, OrderProcessorException{
		if(items == null || items.isEmpty()){
			throw new ParameterValidationException("A set of items with order records is required");
		}
		ArrayList<ItemSummaryDTO> itemSummaries = new ArrayList<ItemSummaryDTO>();
		LocalDate firstDayLogistics = LocalDate.MAX, lastDayLogistics = LocalDate.MIN;
		double totalCost = 0.0;
		int firstDay = 0, lastDay = 0;
		for(ItemDTO i : items.keySet()){
			int committed = 0, backordered = 0, sources = 0, firstDayItem = 0, lastDayItem = 0;
			double costItem = 0.0;
			LocalDate itemFirstDay = LocalDate.MAX, itemLastDay = LocalDate.MIN;
		
			for(OrderRecordCommitted o : items.get(i)){
				switch(o.getOrderRecordStatus()){
					case COMMITTED:
						committed += o.getNumItems();
						itemFirstDay = (itemFirstDay.isBefore(o.getDeliveryDay())) ? itemFirstDay : o.getDeliveryDay();
						itemLastDay = (itemLastDay.isAfter(o.getDeliveryDay())) ? itemLastDay : o.getDeliveryDay();
						//costItem += o.getProcessCost() + (i.price * o.getNumItems());
						costItem += o.getProcessCost() + o.getItemsCost();
						costItem +=  OrderProcessorSvc.getInstance().getTransportCost(o.getNumDaysTravel(), o.getSource().name, order.facility);
						sources++;
						break;
					case BACKORDERED:
						backordered += o.getNumItems();
						break;
				}
			}
			
			totalCost += costItem;
			firstDayLogistics = (firstDayLogistics.isBefore(itemFirstDay)) ? firstDayLogistics : itemFirstDay;
			lastDayLogistics = (lastDayLogistics.isAfter(itemLastDay)) ? lastDayLogistics : itemLastDay;
			firstDayItem = (int) ChronoUnit.DAYS.between(LocalDate.now(), itemFirstDay);
			lastDayItem = (int) ChronoUnit.DAYS.between(LocalDate.now(), itemLastDay);
			itemSummaries.add(new ItemSummaryDTO(i.code, committed, backordered, sources, costItem, firstDayItem, lastDayItem));
		}
		itemSummaries.sort(new ItemCodeComparator());
		firstDay = (int) ChronoUnit.DAYS.between(LocalDate.now(), firstDayLogistics);
		lastDay = (int) ChronoUnit.DAYS.between(LocalDate.now(), lastDayLogistics);
		return new OrderProcessedDTO(this.retrieveOrder(this.order.orderId), itemSummaries, totalCost, firstDay, lastDay);
	}

}
