package com.johnmarkese.se450.dataloader;

import org.w3c.dom.Document;

public class DataLoaderXML {
	private Document doc;

	public DataLoaderXML(String path) throws DataLoaderException {
		FileLoader xml = FileLoaderFactory.createDataLoader(path);
		this.doc = (Document) xml.load();
	}
	
	public Document getDoc(){
		return this.doc;
	}
}
