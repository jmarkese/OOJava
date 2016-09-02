package com.johnmarkese.se450.itemsvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.DataLoaderFactory;

public class ItemLoaderFactory extends DataLoaderFactory {
	private static String config;
	private static String ext;
	
	public ItemLoaderFactory() throws DataLoaderException{
		config = getConfigPath("items");
		ext = getFileExtension(config);		
	}

	@Override
	public ItemLoader dataLoader() throws DataLoaderException {
		if (ext.equals(".xml")) {
			return new ItemLoaderXMLImpl(config);
		} else {
			throw new DataLoaderException("The filetype " + ext + " is not supported");
		}
	}
}
