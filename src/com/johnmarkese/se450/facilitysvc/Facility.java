package com.johnmarkese.se450.facilitysvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.inventorysvc.InventoryDepletedException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public interface Facility {
	public Map<String, Integer> getInventory() throws FacilityException;
	public int searchInventory(String itemCode) throws FacilityException;
	public ScheduleDTO getSchedule(int days) throws FacilityException, ScheduleException;
	public FacilityDTO getDTO() throws FacilityException, ScheduleException;
	public String getName();
	public ArrayList<Integer> getDaysSlots(LocalDate date, int days) throws FacilityException;
	public LocalDate earliestCompleteDate(LocalDate day, int units) throws FacilityException, ScheduleException;
	public void bookSchedule(LocalDate start, String item, int units) throws ScheduleException, InventoryDepletedException, ParameterValidationException, DataLoaderException, InventoryException;
}
