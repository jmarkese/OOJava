package com.johnmarkese.se450.networksvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.*;
import com.johnmarkese.se450.networksvc.shortestpath.*;
import com.johnmarkese.se450.utils.ParameterValidationException;

public class NetworkLoaderXMLImpl implements NetworkLoader {

	private DataLoaderXML doc;

	public NetworkLoaderXMLImpl(String path) throws DataLoaderException {
		this.doc = new DataLoaderXML(path);
	}

	@Override
	public Map<String, Vertex> load() throws DataLoaderException, DOMException, ParameterValidationException, ParserConfigurationException, SAXException, IOException, ShortestPathException {
		NodeList facilities = this.doc.getDoc().getDocumentElement().getChildNodes();
		ArrayList<String> names = new ArrayList<String>();
		for (int i = 0; i < facilities.getLength(); i++) {
			if (facilities.item(i).getNodeType() == Node.TEXT_NODE) {
				continue;
			}

			String entryName = facilities.item(i).getNodeName();
			if (!entryName.equals("facility")) {
				continue;
			}

			NamedNodeMap aMap = facilities.item(i).getAttributes();
			String facilityName = aMap.getNamedItem("name").getNodeValue();
			names.add(facilityName);
		}
		return networkGraph(names);
	}
	
	@Override
	public WeightModifier loadModifier() throws ShortestPathException, ParameterValidationException{
		return WeightModifierFactory.getInstance(loadModifierConditions()).modifier("standard");
	}
	
	@Override
	public double loadTransportationFee() {
		NodeList feeNode = doc.getDoc().getElementsByTagName("transportation_fee");
		String fee = feeNode.item(0).getAttributes().getNamedItem("fee").getTextContent();
		return Double.parseDouble(fee);
	}
	
	private Map<String, Vertex> networkGraph(List<String> names)
			throws DataLoaderException, ParameterValidationException, DOMException, ParserConfigurationException,
			SAXException, IOException, ShortestPathException {
		HashMap<String, Vertex> networkMap = new HashMap<String, Vertex>();
		for (String n : names) {
			Vertex v = VertexFactory.vertex(n);
			networkMap.put(v.getName(), v);
		}
		loadLinks(networkMap);
		return networkMap;

	}

	private void loadLinks(HashMap<String, Vertex> networkMap)
			throws DataLoaderException, DOMException, ParserConfigurationException, SAXException, IOException,
			ParameterValidationException, ShortestPathException {

		NodeList network = doc.getDoc().getDocumentElement().getElementsByTagName("transportation_links");

		for (int i = 0; i < network.getLength(); i++) {
			if (network.item(i).getNodeType() == Node.TEXT_NODE) {
				continue;
			}
			String entryName = network.item(i).getNodeName();
			if (!entryName.equals("transportation_links")) {
				continue;
			}
			NamedNodeMap aMap = network.item(i).getAttributes();
			String facilityName = aMap.getNamedItem("name").getNodeValue();
			Vertex facilty = networkMap.get(facilityName);

			// TODO error check this mess.
			// What if a link to doesn't exist?
			// What if distance is incorrect?
			// What if xml is malformed?
			Element elem = (Element) network.item(i);
			NodeList links = elem.getElementsByTagName("link");

			for (int j = 0; j < links.getLength(); j++) {
				Element link = (Element) links.item(j);
				String linkTo = link.getElementsByTagName("link_to").item(0).getTextContent();
				double distance = Double.parseDouble(link.getElementsByTagName("distance").item(0).getTextContent());
				Vertex faciltyTo = networkMap.get(linkTo);
				Edge edge = EdgeFactory.edge(faciltyTo, distance, loadModifier());
				facilty.addEdge(edge);
			}

		}

	}

	private Map<String, ArrayList<Double>> loadModifierConditions() {
		HashMap<String, ArrayList<Double>> conditions = new HashMap<String, ArrayList<Double>>();
		NodeList cond = doc.getDoc().getElementsByTagName("travel_condition");
		for (int i = 0; i < cond.getLength(); i++) {
			String key = cond.item(i).getAttributes().getNamedItem("name").getTextContent();
			ArrayList<Double> values = new ArrayList<Double>();
			Double hours = Double.valueOf(cond.item(i).getAttributes().getNamedItem("hours_per_day").getTextContent());
			Double mph = Double.valueOf(cond.item(i).getAttributes().getNamedItem("speed").getTextContent());
			values.add(hours);
			values.add(mph);
			conditions.put(key, values);
		}
		return conditions;
	}

}