package com.johnmarkese.se450.networksvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.networksvc.shortestpath.Edge;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPath;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.networksvc.shortestpath.Vertex;
import com.johnmarkese.se450.networksvc.shortestpath.WeightModifier;
import com.johnmarkese.se450.utils.ParameterValidationException;

public final class NetworkSvc {

	private volatile static NetworkSvc instance;
	private static HashMap<String, Vertex> network;
	private static ShortestPath sp;
	private static WeightModifier modifier;
	private static double transportationFee;
	
	public enum Round { 
		UP, DOWN 
	}

	private NetworkSvc() throws DataLoaderException, DOMException, ParameterValidationException,
			ParserConfigurationException, SAXException, IOException, ShortestPathException {
		loadNetwork();
	}

	public static NetworkSvc getInstance() throws DataLoaderException, DOMException, ParameterValidationException,
			ParserConfigurationException, SAXException, IOException, ShortestPathException {
		if (instance == null) {
			synchronized (NetworkSvc.class) {
				if (instance == null) {
					instance = new NetworkSvc();
				}
			}
		}
		return instance;
	}

	private static void loadNetwork() throws DataLoaderException, DOMException, ParameterValidationException,
			ParserConfigurationException, SAXException, IOException, ShortestPathException {
		NetworkLoader nl = new NetworkLoaderFactory().dataLoader();
		network = (HashMap<String, Vertex>) nl.load();
		modifier = nl.loadModifier();
		setTransportationFee(nl.loadTransportationFee());
	}

	public static HashMap<String, Double> getNeighbors(String name) throws NetworkException {
		if (name.isEmpty()) {
			throw new NetworkException("The source facility name must be set.");
		} else if (network.get(name) == null) {
			throw new NetworkException("The source facility " + name + " does not exist.");
		}
		ArrayList<Edge> neighbors = (ArrayList<Edge>) network.get(name).getEdges();
		HashMap<String, Double> neighborMap = new HashMap<String, Double>();
		for (Edge e : neighbors) {
			neighborMap.put(e.getTo(), e.getWeightModified());
		}
		return neighborMap;
	}

	public static List<String> shortestPath(String a, String b)
			throws ParameterValidationException, ShortestPathException {
		Vertex v1 = network.get(a);
		Vertex v2 = network.get(b);
		if (v1 == null) {
			throw new ShortestPathException(a + " does not exist in the network");
		}
		if (v2 == null) {
			throw new ShortestPathException(b + " does not exist in the network");
		}
		sp = new ShortestPath(v1, v2, network.values(), modifier);
		ArrayList<String> list = new ArrayList<String>();
		return sp.getPath();
	}
	
	public static double getDistance(){
		return sp.getDistance();
	}
	
	public static double getDistanceModified(){
		return sp.getDistanceModified();
	}
	
	public static double getDistanceModified(Round r) throws ParameterValidationException{
		if (r == null) {
			throw new ParameterValidationException ("Rounding must be set.");			
		} else if (r.equals(Round.UP)){
			return Math.ceil(sp.getDistanceModified());			
		} else if (r.equals(Round.DOWN)){
			return Math.floor(sp.getDistanceModified());			
		} else {
			// can this even happen with enum?
			throw new ParameterValidationException ("Rounding must be set to either Round.UP or Round.DOWN");
		}
	}
	
	public static double getDistanceModified(String a, String b, Round r) throws ParameterValidationException, ShortestPathException{
		shortestPath(a, b);
		return getDistanceModified(r);
	}
	

	/**
	 * @return the transportationFee
	 */
	public static double getTransportationFee() {
		return transportationFee;
	}

	/**
	 * @param transportationFee the transportationFee to set
	 */
	private static void setTransportationFee(double transportationFee) {
		NetworkSvc.transportationFee = transportationFee;
	}
}
