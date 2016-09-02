package com.johnmarkese.se450.inventorysvc;

import org.w3c.dom.Document;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.DataLoaderFactory;
import com.johnmarkese.se450.dataloader.FileLoader;
import com.johnmarkese.se450.dataloader.FileLoaderFactory;

public class InventoryLoaderFactory extends DataLoaderFactory {
	private static String config;
	private static String ext;
	private static Document doc;
	
	public InventoryLoaderFactory() throws DataLoaderException {
		config = getConfigPath("inventories");
		ext = getFileExtension(config);
		if (ext.equals(".xml")) {
			FileLoader xml = FileLoaderFactory.createDataLoader(config);
			doc = (Document) xml.load();
		}
	}

	@Override
	public InventoryLoader dataLoader() throws DataLoaderException {
		if (ext.equals(".xml")) {
			return new InventoryLoaderXMLImpl(doc);
		} else {
			throw new DataLoaderException("The filetype " + ext + " is not supported");
		}
	}
}
