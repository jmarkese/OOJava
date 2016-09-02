package com.johnmarkese.se450.networksvc.shortestpath;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class EdgeFactory {
	private EdgeFactory(){}
	
	public static Edge edge(Vertex to, double weight) throws ParameterValidationException, ShortestPathException {
		// TODO add more error checking
		if (to == null) {
			throw new ParameterValidationException("Vertex to must be set (cannot be null)");
		} else if (weight < 0) {
			throw new ParameterValidationException("Edge weight must be a positive number");
		} else {
			return new EdgeImpl(to, weight);
		} 
	}

	public static Edge edge(Vertex to, double weight, WeightModifier modifier) throws ParameterValidationException, ShortestPathException {
		// TODO Auto-generated method stub
		if (to == null) {
			throw new ParameterValidationException("Vertex to must be set (cannot be null)");
		} else if (modifier == null) {
			throw new ParameterValidationException("WeightModifier modifier be set (cannot be null)");
		} else if (weight < 0) {
			throw new ParameterValidationException("Edge weight must be a positive number");
		} else {
			return new EdgeImpl(to, weight, modifier);
		}
	}

}