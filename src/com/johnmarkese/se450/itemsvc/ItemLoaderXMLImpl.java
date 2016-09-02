package com.johnmarkese.se450.itemsvc;

import java.util.ArrayList;
import java.util.List;

import com.johnmarkese.se450.utils.ParameterValidationException;
import org.w3c.dom.*;
import com.johnmarkese.se450.dataloader.*;

public class ItemLoaderXMLImpl implements ItemLoader {

	private Document doc;

	public ItemLoaderXMLImpl(String path) throws DataLoaderException {
		FileLoader xml = FileLoaderFactory.createDataLoader(path);
		this.doc = (Document) xml.load();
	}

	@Override
	public List<Item> load() throws DataLoaderException {
		try {
			ArrayList<Item> itemsList = new ArrayList<Item>();
			NodeList items = this.doc.getDocumentElement().getElementsByTagName("item");
			for (int i = 0; i < items.getLength(); i++) {
				if (items.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}
				String entryName = items.item(i).getNodeName();
				if (!entryName.equals("item")) {
					continue;
				}

				Element elem = (Element) items.item(i);
				String code = elem.getElementsByTagName("item_code").item(0).getTextContent();
				double price = Double.parseDouble(elem.getElementsByTagName("item_price").item(0).getTextContent());

				Item item = ItemFactory.item(code, price);
				itemsList.add(item);
			}
			return itemsList;
		} catch (ParameterValidationException | DOMException  e) {
			e.printStackTrace();
			throw new DataLoaderException(e.getMessage());
		}
	}
}
