package com.johnmarkese.se450.inventorysvc;

import java.util.HashMap;
import java.util.Map;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.itemsvc.Item;
import com.johnmarkese.se450.itemsvc.ItemDTO;
import com.johnmarkese.se450.itemsvc.ItemSvc;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class InventoryImpl implements Inventory {

	private HashMap<ItemDTO, Integer> inventory;

	public InventoryImpl(Map<ItemDTO, Integer> inventory) throws ParameterValidationException {
		setIntenvory(inventory);
	}

	private void setIntenvory(Map<ItemDTO, Integer> inventory) throws ParameterValidationException {
		if (inventory == null) {
			throw new ParameterValidationException("Map<ItemDTO, Integer> inventory need to be initlaized");
		}
		this.inventory = new HashMap<ItemDTO, Integer>(inventory);
	}
	
	private ItemDTO getItemDTO(String item) throws ParameterValidationException, DataLoaderException, InventoryException{
		if (item == null || item.isEmpty()) {
			throw new ParameterValidationException("The item code cannot be null or empty.");
		}
		ItemDTO iDTO = ItemSvc.getInstance().getItem(item);
		if (iDTO == null) {
			throw new InventoryException("The item " + item + " doesnt exist.");
		}
		return iDTO;
	}

	// NOTE the methods to modify the inventory obviously need to be
	// implemtented

	@Override
	public int itemQty(String itemCode) throws InventoryException, ParameterValidationException {
		try {
			ItemDTO item = getItemDTO(itemCode);
			return (inventory.get(item) != null) ? inventory.get(item) : 0;
		} catch (DataLoaderException e) {
			e.printStackTrace();
			throw new InventoryException(e.getMessage());
		}
	}

	@Override
	public void itemAdd(Item item) throws InventoryException, ParameterValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public HashMap<ItemDTO, Integer> getInventory() throws InventoryException, ParameterValidationException {
		// TODO Auto-generated method stub
		return this.inventory;
	}

	@Override
	public void stockAdd(String item, int amount) throws InventoryException, ParameterValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void stockRemove(String item, int amount) throws InventoryDepletedException, ParameterValidationException, DataLoaderException, InventoryException {
		if (amount < 1) {
			throw new ParameterValidationException("The number of stock items to be removed must be a postive integer.");
		}
		ItemDTO iDTO = getItemDTO(item);
		if(!inventory.containsKey(iDTO)){
			throw new InventoryException("The item " + item + " is not stocked at this location.");
		}
		int stock = inventory.get(iDTO);
		if (amount > stock){
			throw new InventoryException("The removal amount " + amount + " cannot be greater than the stocked number of " + stock + " "  + item + "(s).");			
		}
		inventory.put(iDTO, stock - amount);
	}

	@Override
	public void itemAdd(ItemDTO item) throws InventoryException, ParameterValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public void itemRemove(ItemDTO item) throws InventoryException, ParameterValidationException {
		// TODO Auto-generated method stub

	}

	@Override
	public Map<String, Integer> getDTO() throws InventoryException {
		// TODO Auto-generated method stub
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		try {
			for (ItemDTO i : this.getInventory().keySet()) {
				String key = i.code;
				map.put(key, this.getInventory().get(i));
			}
		} catch (ParameterValidationException e) {
			e.printStackTrace();
			throw new InventoryException(e.getMessage());
		}
		return map;
	}

}
