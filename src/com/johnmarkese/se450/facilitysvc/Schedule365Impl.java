package com.johnmarkese.se450.facilitysvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedMap;
import java.util.TreeMap;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class Schedule365Impl implements Schedule {

	// at some point this should hold something other than Integer (Array of Item ids or ItemDTOs)
	private SortedMap<LocalDate, Integer> schedule;
	private int maxSlots;

	public Schedule365Impl(double slots) throws ParameterValidationException, ScheduleException {
		this.setMaxSlots(slots);
		schedule = new TreeMap<LocalDate, Integer>();
		schedule.put(LocalDate.now(), 0);
	}
	
	@Override
	public boolean dayOperational(LocalDate date) throws ScheduleException {
		if (date == null || date.isBefore(LocalDate.now())) {
			throw new ScheduleException("The date specified must be initilized and cannot be in the past");
		}
		//if(date.getDayOfWeek().getValue() > 5) { return false; }//for testing... this would be a M-F schedule 
		return true;
	}

	@Override
	public SortedMap<LocalDate, Integer> getSchedule() {
		return schedule;
	}

	@Override
	public ScheduleDTO getDTO(int days) throws ScheduleException {
		// TODO Auto-generated method stub
		int[] daysArr = new int[days];
		return new ScheduleDTO(daysArr);
	}
	
	@Override
	public ArrayList<Integer> getDaysSlots(LocalDate start, int numDays) throws ScheduleException{
		ArrayList<Integer> ds = new ArrayList<Integer>();
		for(int i = 0; i < numDays; i++){
			ds.add(getOpenSlots(start.plusDays(i)));
		}
		return ds;
	}
	
	@Override
	public LocalDate earliestCompleteDate(LocalDate day, int units) throws ScheduleException{
		while(units > 0){
			day = day.plusDays(1);
			units -= getOpenSlots(day);
		}
		return day;
	}

	@Override
	public void book(int slots, LocalDate date) throws ScheduleException {
		Integer temp = this.schedule.get(date);
		int booked = (temp == null) ? 0 : temp;
		int space = (dayOperational(date)) ? this.maxSlots - booked : 0;

		if (slots <= space) {
//////////////System.out.print("[" + date.toString() + " slots:" + slots + " space:" + space +"]");
			schedule.put(date, booked + slots);
			return;
		} else {
//////////////System.out.print("[" + date.toString() + " slots:" + slots + " space:" + space +"]");
			schedule.put(date, space);
			book(slots -= space, date.plusDays(1));
		}
	}
	
	private int getOpenSlots(LocalDate date) throws ScheduleException{
		Integer temp = this.schedule.get(date);
		int booked = (temp == null) ? 0 : temp;
		return (dayOperational(date)) ? this.maxSlots - booked : 0;
	}
	
	private void setMaxSlots(double slots) throws ParameterValidationException {
		int i = (int) slots;
		this.setMaxSlots(i);
	}

	private void setMaxSlots(int slots) throws ParameterValidationException {
		if (slots < 0) {
			throw new ParameterValidationException("Parameter slots must be a non-negative number.");
		}
		this.maxSlots = slots;
	}

}
