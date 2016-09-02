package com.johnmarkese.se450.facilitysvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.SortedMap;

public interface Schedule {
	public boolean dayOperational(LocalDate date) throws ScheduleException;

	public SortedMap<LocalDate, Integer> getSchedule();

	public void book(int slots, LocalDate date) throws ScheduleException;

	public ArrayList<Integer> getDaysSlots(LocalDate date, int i) throws ScheduleException;
	
	public LocalDate earliestCompleteDate(LocalDate start, int units) throws ScheduleException;
	
	public ScheduleDTO getDTO(int days) throws ScheduleException;
}
