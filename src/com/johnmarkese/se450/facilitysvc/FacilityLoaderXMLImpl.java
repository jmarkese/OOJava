package com.johnmarkese.se450.facilitysvc;

import java.util.ArrayList;
import java.util.Collection;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.dataloader.FileLoader;
import com.johnmarkese.se450.dataloader.FileLoaderFactory;
import com.johnmarkese.se450.inventorysvc.InventoryException;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class FacilityLoaderXMLImpl implements FacilityLoader {

	private Document doc;

	public FacilityLoaderXMLImpl(String path) throws DataLoaderException {
		// TODO error check this
		FileLoader xml = FileLoaderFactory.createDataLoader(path);
		this.doc = (Document) xml.load();
	}

	@Override
	public Collection<Facility> load() throws DataLoaderException {
		try {
			ArrayList<Facility> facilityList = new ArrayList<Facility>();
			NodeList items = doc.getDocumentElement().getElementsByTagName("facility");
			for (int i = 0; i < items.getLength(); i++) {
				if (items.item(i).getNodeType() == Node.TEXT_NODE) {
					continue;
				}
				String entryName = items.item(i).getNodeName();
				if (!entryName.equals("facility")) {
					continue;
				}

				Element elem = (Element) items.item(i);
				String name = elem.getElementsByTagName("facility_name").item(0).getTextContent();
				double rate = Double.parseDouble(elem.getElementsByTagName("rate_per_day").item(0).getTextContent());
				double cost = Double.parseDouble(elem.getElementsByTagName("cost").item(0).getTextContent());
				
				Facility facility = FacilityFactory.facility(name, rate, cost);
				facilityList.add(facility);
			}
			return facilityList;
		} catch (ParameterValidationException | DOMException | InventoryException | FacilityException | ScheduleException  e) {
			e.printStackTrace();
			throw new DataLoaderException(e.getMessage());
		}
	}
}
