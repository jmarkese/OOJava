package com.johnmarkese.se450.inventorysvc;

import java.util.HashMap;
import java.util.Map;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.itemsvc.ItemSvc;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class InventorySvc {
	private volatile static InventorySvc instance;
	private static HashMap<String, Inventory> inventories;
	private static InventoryLoaderFactory dl;
	private static ItemSvc itemSvc;

	private InventorySvc() throws InventoryException, DataLoaderException {
		dl = new InventoryLoaderFactory();
		itemSvc = ItemSvc.getInstance();
	}

	public static InventorySvc getInstance() throws InventoryException, DataLoaderException {
		if (instance == null){
			synchronized (InventorySvc.class) {
				if (instance == null){
					instance = new InventorySvc();
				}
			}
		}
		return instance;
	}
	
	public static Inventory getInventory (String location) throws ParameterValidationException, DataLoaderException, InventoryException{
		InventoryLoader il = dl.dataLoader();
		Map<ItemDTO, Integer> data = il.load(location);
		return InventoryFactory.inventory(data);
	}
}
