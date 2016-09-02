package com.johnmarkese.se450.ordersvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.DataLoaderFactory;

public class OrderLoaderFactory extends DataLoaderFactory {
	private static String config;
	private static String ext;

	public OrderLoaderFactory() throws DataLoaderException{
		config = getConfigPath("orders");
		ext = getFileExtension(config);		
	}

	@Override
	public OrderLoader dataLoader() throws DataLoaderException {
		if (ext.equals(".xml")) {
			return new OrderLoaderXMLImpl(config);
		} else {
			throw new DataLoaderException("The filetype " + ext + " is not supported");
		}
	}
}
