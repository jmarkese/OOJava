package com.johnmarkese.se450.dataloader;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class LocalXmlFileLoaderImpl implements FileLoader {
	private String path;

	public LocalXmlFileLoaderImpl(String pathArg) {
		path = pathArg;
		
		
		//System.out.println(pathArg);
	}

	public Document load() throws DataLoaderException {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			File xmlDoc = new File(path);

			if (!xmlDoc.exists()) {
				throw new DataLoaderException("XML File '" + path + "' cannot be found");
			}

			Document doc = db.parse(xmlDoc);//System.out.println(path + doc.getNodeName());
			doc.getDocumentElement().normalize();//System.out.println(path + doc.getDocumentElement().getChildNodes().getLength());
			return doc;
		} catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
			throw new DataLoaderException(e.getMessage());
		}
	}
}