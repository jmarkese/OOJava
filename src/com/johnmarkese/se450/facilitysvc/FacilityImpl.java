package com.johnmarkese.se450.facilitysvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.inventorysvc.Inventory;
import com.johnmarkese.se450.inventorysvc.InventoryDepletedException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class FacilityImpl implements Facility {
	private Schedule schedule;
	private Inventory inventory;
	private String name;
	private double rate;
	private double cost;
	
	public FacilityImpl(String name, double rate, double cost, Schedule schedule, Inventory inventory) throws ParameterValidationException, ScheduleException  {
		this.setName(name);
		this.setRate(rate);
		this.setCost(cost);
		this.setSchedule(schedule);
		this.setInventory(inventory);
	}
	
	private void setInventory(Inventory inventory) throws ParameterValidationException {
		//InventorySvc InvSvc = InventorySvc.getInstance();System.out.println(this.getName());
		this.inventory = inventory;//InvSvc.getInventory(this.getName());
	}

	private void setCost(double cost)   throws ParameterValidationException{
		if(!Double.isFinite(cost) || cost < 0){
			throw new ParameterValidationException("Facilty cost must be a non-negative finite number.");
		}
		this.cost = cost;		
	}
	
	private void setRate(double rate)  throws ParameterValidationException{
		if(!Double.isFinite(rate) || rate < 0){
			throw new ParameterValidationException("Facilty rate must be a non-negative finite number.");
		}
		this.rate = rate;			
	}

	private void setName(String name) throws ParameterValidationException{
		if(name.isEmpty()){
			throw new ParameterValidationException("Facilty name must be set");
		}
		this.name = name;
	}

	private void setSchedule(Schedule schedule) throws ParameterValidationException, ScheduleException {
		if (schedule == null) {
			throw new ParameterValidationException("Schedule cannot be null");
		}
		this.schedule = schedule;
	}

	@Override
	public Map<String, Integer> getInventory() throws FacilityException {
		try {
			return this.inventory.getDTO();
		} catch (InventoryException e) {
			e.printStackTrace();
			throw new FacilityException(e.getMessage());
		}
	}

	@Override
	public ArrayList<Integer> getDaysSlots(LocalDate date, int days) throws FacilityException {
		//ArrayList<Integer> schedule = new ArrayList<Integer>(this.schedule.getSchedule().values());
		try {
			return this.schedule.getDaysSlots(date, days);
		} catch (ScheduleException e) {
			e.printStackTrace();
			throw new FacilityException(e.getMessage());
		}
	}

	@Override
	public FacilityDTO getDTO() throws FacilityException, ScheduleException {
		ArrayList<Integer> slots = schedule.getDaysSlots(LocalDate.now(), 20);
		return new FacilityDTO(this.getName(), (int) this.getRate(), this.getCost(), schedule.getDaysSlots(LocalDate.now(), 20));
	}
	
	@Override
	public int searchInventory(String itemCode) throws FacilityException {
		try {
			return this.inventory.itemQty(itemCode);
		} catch (InventoryException | ParameterValidationException e) {
			e.printStackTrace();
			throw new FacilityException(e.getMessage());
		}
	}	

	private double getRate() {
		return this.rate;
	}

	private double getCost() {
		return this.cost;
	}

	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString(){
		return "{'name':'" + this.getName() +"','rate':"+ this.getRate() + ",'cost':" + this.getCost()+"}";
	}

	@Override
	public ScheduleDTO getSchedule(int days) throws FacilityException, ScheduleException {
		return schedule.getDTO(days);
	}

	@Override
	public LocalDate earliestCompleteDate(LocalDate day, int units) throws FacilityException, ScheduleException {
		return schedule.earliestCompleteDate(day, units);
	}

	@Override
	public void bookSchedule(LocalDate start, String item,  int units) throws ScheduleException, InventoryDepletedException, ParameterValidationException, DataLoaderException, InventoryException {
		inventory.stockRemove(item, units);
/////////////////////System.out.println(getName() + " Bookings:");
		schedule.book(units, start);
	}

}
