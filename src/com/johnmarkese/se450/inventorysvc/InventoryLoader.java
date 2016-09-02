package com.johnmarkese.se450.inventorysvc;

import java.util.Map;

import com.johnmarkese.se450.dataloader.DataLoader;
import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.itemsvc.ItemDTO;

public interface InventoryLoader extends DataLoader{
	public Map<ItemDTO, Integer> load(String location) throws DataLoaderException;
}
