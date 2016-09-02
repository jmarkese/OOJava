package com.johnmarkese.se450.facilitysvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.inventorysvc.Inventory;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.inventorysvc.InventorySvc;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class FacilityFactory {
	private FacilityFactory() {
	}

	public static Facility facility(String name, double rate, double cost)
			throws ParameterValidationException, DataLoaderException, InventoryException, FacilityException, ScheduleException {
		// TODO add more error checking
		if (name.isEmpty()) {
			throw new ParameterValidationException("Name must be set");
		} else if (!Double.isFinite(rate) || rate < 0) {
			throw new ParameterValidationException("Rate must be a non-negative finite number");
		} else if (!Double.isFinite(cost) || cost < 0) {
			throw new ParameterValidationException("Cost must be a non-negative finite number");
		}
		Schedule calendar = ScheduleFactory.schedule(name, rate);
		Inventory inventory = InventorySvc.getInstance().getInventory(name);
		return new FacilityImpl(name, rate, cost, calendar, inventory);
	}

}
