package com.johnmarkese.se450.itemsvc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.johnmarkese.se450.dataloader.DataLoaderException;

public class ItemSvc {
	private volatile static ItemSvc instance;
	private static HashMap<String, Item> catalogMap;
	private static ArrayList<Item> catalogList;
	
	private ItemSvc() throws DataLoaderException {
		loadItems();
	}

	public static ItemSvc getInstance() throws DataLoaderException {
		if (instance == null) {
			synchronized (ItemSvc.class) {
				if (instance == null) {
					instance = new ItemSvc();
				}
			}
		}
		return instance;
	}

	public static ItemDTO getItem(String code) {
		Item item = catalogMap.get(code);
		return item.getDTO();
	}

	public static List<ItemDTO> getCatalog() {
		ArrayList<ItemDTO> catalogDTO = new ArrayList<ItemDTO>();
		for (Item i : catalogList) {
			catalogDTO.add(i.getDTO());
		}		
		return catalogDTO;
	}

	public boolean itemExists(String item) {
		return (catalogMap.get(item) != null);
	}
	
	private static void loadItems() throws DataLoaderException {
		ItemLoader il = new ItemLoaderFactory().dataLoader();
		catalogList = new ArrayList<Item>(il.load());
		catalogMap = new HashMap<String, Item>();
		for (Item i : catalogList) {
			catalogMap.put(i.getCode(), i);
		}
	}
}
