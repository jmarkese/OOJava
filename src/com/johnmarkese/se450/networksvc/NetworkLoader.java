package com.johnmarkese.se450.networksvc;

import java.io.IOException;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

import com.johnmarkese.se450.dataloader.DataLoader;
import com.johnmarkese.se450.dataloader.DataLoaderException;
import com.johnmarkese.se450.networksvc.shortestpath.ShortestPathException;
import com.johnmarkese.se450.networksvc.shortestpath.Vertex;
import com.johnmarkese.se450.networksvc.shortestpath.WeightModifier;
import com.johnmarkese.se450.utils.ParameterValidationException;

public interface NetworkLoader extends DataLoader{
	public Map<String, Vertex> load() throws DataLoaderException, DOMException, ParameterValidationException, ParserConfigurationException, SAXException, IOException, ShortestPathException;
	public WeightModifier loadModifier() throws ShortestPathException, ParameterValidationException;
	public double loadTransportationFee();
}
