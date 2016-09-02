package com.johnmarkese.se450.facilitysvc;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.DataLoaderFactory;

public class FacilityLoaderFactory extends DataLoaderFactory {
	private static String config;
	private static String ext;
	
	public FacilityLoaderFactory() throws DataLoaderException {
		config = getConfigPath("facilities");
		ext = getFileExtension(config);		
	}

	public FacilityLoader dataLoader() throws DataLoaderException {
		if (ext.equals(".xml")) {
			return new FacilityLoaderXMLImpl(config);
		} else {
			throw new DataLoaderException("The filetype " + ext + " is not supported");
		}
	}
}
