package com.johnmarkese.se450.facilitysvc;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.inventorysvc.InventoryDepletedException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
//import com.johnmarkese.se450.inventorysvc.InventorySvc;
import com.johnmarkese.se450.networksvc.NetworkException;
import com.johnmarkese.se450.networksvc.NetworkSvc;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
//import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class FacilitySvc {
	private volatile static FacilitySvc instance;
	private static HashMap<String, Facility> facilityMap;
	private static ArrayList<Facility> facilityList;
	private static NetworkSvc network;
	//private static InventorySvc inventory;
	
	private FacilitySvc () throws DataLoaderException, InventoryException, DOMException, ParameterValidationException, ParserConfigurationException, SAXException, IOException, ShortestPathException {
		network = NetworkSvc.getInstance();
		//inventory = InventorySvc.getInstance();
		loadFacilites();
	}
	
	public static FacilitySvc getInstance() throws DataLoaderException, InventoryException, DOMException, ParameterValidationException, ParserConfigurationException, SAXException, IOException, ShortestPathException {
		if (instance == null){
			synchronized (FacilitySvc.class) {
				if (instance == null){
					instance = new FacilitySvc();
				}
			}
		}
		return instance;
	}
		
	public ArrayList<FacilityDTO> getFacilities() throws FacilityException, ScheduleException {
		ArrayList<FacilityDTO> fDTO = new ArrayList<FacilityDTO>();
		for(Facility f : facilityList){
			fDTO.add(f.getDTO());
		}
		return fDTO;
	}

	public Map<String, Integer> getInventory(String name) throws FacilityException {
		return facilityMap.get(name).getInventory();
	}
	
	public ArrayList<Integer> getDaysSlots(String name, int i, LocalDate day) throws FacilityException {
		return facilityMap.get(name).getDaysSlots(day, i);
	}

	public static HashMap<String, Double> getNeighbors(String name) throws DataLoaderException, InventoryException, NetworkException, DOMException, ParameterValidationException, ParserConfigurationException, SAXException, IOException, ShortestPathException {
		Facility facility = getInstance().facilityMap.get(name);
		return network.getNeighbors(facility.getName());
	}
	
	public FacilityDTO getFacility(String name) throws FacilityException, ScheduleException{
		if(facilityMap.get(name) == null) throw new FacilityException("The facility " + name + " does not exist.");
		return facilityMap.get(name).getDTO();
	}
	
	public boolean facilityExists(String name){
		return (facilityMap.get(name) != null);
	}

	public HashMap<FacilityDTO, Integer> findFacilityItem(String item) throws FacilityException, ScheduleException {
		HashMap<FacilityDTO, Integer> facilityItems = new HashMap<FacilityDTO, Integer>();
		for(Facility f : facilityList){
			int numItems = f.searchInventory(item);
			if(numItems > 0){
				facilityItems.put(f.getDTO(), numItems);
			}
		}
		return facilityItems;
	}
	
	private static void loadFacilites() throws DataLoaderException {
		FacilityLoader fl = new FacilityLoaderFactory().dataLoader();
		facilityList = new ArrayList<Facility>(fl.load());
		facilityMap = new HashMap<String, Facility>();
		for (Facility f : facilityList) {
			facilityMap.put(f.getName(), f);
		}
	}

	public LocalDate getEarliestCompleteDate(String facility, LocalDate day, int units) throws FacilityException, ScheduleException {
		return facilityMap.get(facility).earliestCompleteDate(day, units);
	}

	public void bookFacilty(String facility, int units, String item, LocalDate start) throws ScheduleException, InventoryDepletedException, ParameterValidationException, DataLoaderException, InventoryException {
		facilityMap.get(facility).bookSchedule(start, item, units);
	}


}
