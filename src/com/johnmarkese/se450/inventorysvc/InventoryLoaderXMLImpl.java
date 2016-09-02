package com.johnmarkese.se450.inventorysvc;

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.FileLoader;
import com.johnmarkese.se450.dataloader.FileLoaderFactory;
import com.johnmarkese.se450.itemsvc.ItemDTO;

import com.johnmarkese.se450.itemsvc.ItemSvc;

public class InventoryLoaderXMLImpl implements InventoryLoader {

	private Document doc;

	public InventoryLoaderXMLImpl(String path) throws DataLoaderException {
		// TODO error check this
		FileLoader xml = FileLoaderFactory.createDataLoader(path);
		this.doc = (Document) xml.load();
	}
	
	public InventoryLoaderXMLImpl(Document doc) throws DataLoaderException {
		if(doc == null){
			throw new DataLoaderException("InventoryLoaderXMLImpl (Document doc) must be set.");
		}
		this.doc = doc;
	}

	public Map<ItemDTO, Integer> load(String location) throws DataLoaderException {
		try {
			HashMap<ItemDTO, Integer> inventoryMap = new HashMap<ItemDTO, Integer>();
			NodeList inventories = doc.getDocumentElement().getElementsByTagName("facility_inventory");
			for (int i = 0; i < inventories.getLength(); i++) {
				if (inventories.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}
				String entryName = inventories.item(i).getNodeName();
				if (!entryName.equals("facility_inventory")) {
					continue;
				}

				Element elem = (Element) inventories.item(i);
				String locationName = elem.getElementsByTagName("facility").item(0).getTextContent();
				if (!location.equals(locationName)) {
					continue;
				}
				NodeList items = elem.getElementsByTagName("item");
				for (int j = 0; j < items.getLength(); j++) {
					if (items.item(j).getNodeType() == Node.TEXT_NODE) {
						continue;
					}
					String code = elem.getElementsByTagName("item_code").item(j).getTextContent();
					Integer qty = Integer.parseInt(elem.getElementsByTagName("qty").item(j).getTextContent());
					ItemDTO itemDTO = ItemSvc.getItem(code);
					inventoryMap.put(itemDTO, qty);
				}
				break;
			}
			return inventoryMap;
		} catch (DOMException e) {
			e.printStackTrace();
			throw new DataLoaderException(e.getMessage());
		}
	}
}
