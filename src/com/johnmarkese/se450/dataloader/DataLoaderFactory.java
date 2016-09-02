package com.johnmarkese.se450.dataloader;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public abstract class DataLoaderFactory {
	
	protected static String getConfigPath(String type) throws DataLoaderException{
		Document doc = (Document) FileLoaderFactory.createDataLoader("data/config.xml").load();
		NodeList node = doc.getElementsByTagName("path");//.getElementById(type);
		String path = "";
		if(node == null){
			throw new DataLoaderException("A configuration entry for (" + type + ") does not exist");
		}
		for(int i = 0; i < node.getLength(); i++){
			String nodeType = node.item(i).getAttributes().getNamedItem("id").getTextContent();
			if (nodeType.equals(type)) {
				path = node.item(i).getAttributes().getNamedItem("location").getTextContent();
			}
		}
		if(path.isEmpty()){
			throw new DataLoaderException("The configuration entry for (" + type + ") is invalid");
		}
		return path;	
	}

	protected static String getFileExtension(String path) throws DataLoaderException {
		if (path.length() < 3) {
			throw new DataLoaderException("The path (" + path + ") is too short.");
		}

		int i = path.lastIndexOf('.');
		if (i > 0) {
			return path.substring(i);
		} else {
			throw new DataLoaderException("File extension does not exist");
		}
	}
	
	public abstract DataLoader dataLoader () throws DataLoaderException;

}