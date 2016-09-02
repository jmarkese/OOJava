package com.johnmarkese.se450.inventorysvc;

import java.util.Map;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.itemsvc.Item;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.utils.ParameterValidationException;

public interface Inventory {

	public void itemAdd(Item item) throws InventoryException, ParameterValidationException;

	public Map<ItemDTO, Integer> getInventory() throws InventoryException, ParameterValidationException;

	public void stockAdd(String item, int amount) throws InventoryException, ParameterValidationException;

	public void stockRemove(String item, int amount) throws InventoryDepletedException, ParameterValidationException, DataLoaderException, InventoryException;
	
	public int itemQty(String itemCode) throws InventoryException, ParameterValidationException;

	public void itemAdd(ItemDTO item) throws InventoryException, ParameterValidationException;
	
	public void itemRemove(ItemDTO item) throws InventoryException, ParameterValidationException;
	
	public Map<String, Integer> getDTO() throws InventoryException;
	
}
