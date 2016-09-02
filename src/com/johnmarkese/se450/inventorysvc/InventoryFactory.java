package com.johnmarkese.se450.inventorysvc;

import java.util.Map;

import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;


public class InventoryFactory {
	static Inventory inventory(Map<ItemDTO, Integer> inventoryData) throws ParameterValidationException {
		if(inventoryData == null){
			throw new ParameterValidationException ("The inventory data must be passed");
		}
		return new InventoryImpl(inventoryData);
	}
}