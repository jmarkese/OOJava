package com.johnmarkese.se450.facilitysvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedMap;

public class ScheduleNullImpl implements Schedule {

	private int maxSlots;

	public ScheduleNullImpl(double slots) {
		// TODO Auto-generated constructor stub
		this.maxSlots = (int) slots;
	}

	@Override
	public boolean dayOperational(LocalDate date) throws ScheduleException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public SortedMap<LocalDate, Integer> getSchedule() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> getDaysSlots(LocalDate date, int days) throws ScheduleException {
		ArrayList<Integer> slots = new ArrayList<Integer>();
		for(int i = 0; i < days; i++){
			slots.add(this.maxSlots);
		}
		return slots;
	}

	@Override
	public LocalDate earliestCompleteDate(LocalDate start, int units) throws ScheduleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void book(int slots, LocalDate date) throws ScheduleException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ScheduleDTO getDTO(int days) throws ScheduleException {
		// TODO Auto-generated method stub
		return null;
	}

}
