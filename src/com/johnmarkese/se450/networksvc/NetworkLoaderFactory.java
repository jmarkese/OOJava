package com.johnmarkese.se450.networksvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.DataLoaderFactory;

public class NetworkLoaderFactory extends DataLoaderFactory{
	private static String config;
	private static String ext;
	
	public NetworkLoaderFactory() throws DataLoaderException{
		config = getConfigPath("facilities");
		ext = getFileExtension(config);		
	}

	public NetworkLoader dataLoader() throws DataLoaderException {
		if (ext.equals(".xml")) {
			return new NetworkLoaderXMLImpl(config);
		} else {
			throw new DataLoaderException("The filetype " + ext + " is not supported");
		}
	}
}