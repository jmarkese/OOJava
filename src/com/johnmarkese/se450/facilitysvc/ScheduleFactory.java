package com.johnmarkese.se450.facilitysvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class ScheduleFactory {
	private ScheduleFactory() {
	}

	public static Schedule schedule(String facility, double rate)
			throws ParameterValidationException, FacilityException, DataLoaderException, InventoryException, ScheduleException {
		// TODO add more error checking
		if (facility.isEmpty()) {
			throw new ParameterValidationException("You must specify a facility");
		}
		double slots = rate;//(int) FacilitySvc.getInstance().getFacility(facility).rate;
		if(true)
			return new Schedule365Impl(slots);
		else
			return new ScheduleNullImpl(slots);
	}
}
