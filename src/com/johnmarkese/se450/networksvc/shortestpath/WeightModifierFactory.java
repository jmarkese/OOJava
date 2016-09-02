package com.johnmarkese.se450.networksvc.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class WeightModifierFactory {

	private HashMap<String, ArrayList<Double>> conditions;
	private static WeightModifierFactory instance;

	private WeightModifierFactory() { }

	private WeightModifierFactory(Map<String, ArrayList<Double>> conditions) throws ParameterValidationException {
		setConditions(conditions);
	}

	private void setConditions(Map<String, ArrayList<Double>> conditions) throws ParameterValidationException {
		if(conditions == null){
			throw new ParameterValidationException("WeightModifierFactory needs to be initialized with a collection Map<String, ArrayList<Double>>");
		}
		this.conditions = new HashMap<String, ArrayList<Double>>(conditions);
	}

	public static WeightModifierFactory getInstance() throws ShortestPathException {
		if (instance == null) {
			synchronized (WeightModifierFactory.class) {
				if (instance == null) {
					throw new ShortestPathException("WeightModifierFactory instance has not yet been initialized with a collection Map<String, ArrayList<Double>>");
					//instance = new WeightModifierFactory();
				}
			}
		}
		return instance;
	}

	public static WeightModifierFactory getInstance(Map<String, ArrayList<Double>> conditions) throws ParameterValidationException {
		if (instance == null) {
			synchronized (WeightModifierFactory.class) {
				if (instance == null) {
					instance = new WeightModifierFactory(conditions);
				}
			}
		}
		// Maybe you could update/add conditions here? 
		/* for(String c : conditions.keySet()) { this.conditions.put(c, value); etc...*/
		return instance;
	}

	public WeightModifier modifier(String type) throws ShortestPathException, ParameterValidationException {
		if (type.isEmpty()) {
			throw new ShortestPathException("WeightModifier type must be passed");
		}
		ArrayList<Double> cond = conditions.get(type);
		if (cond == null || cond.size() != 2) {
			throw new ShortestPathException("WeightModifier type " + type +" with " + cond.size() + " conditions is not defined ");
		}
		return new WeightModifierImpl(conditions.get(type));
	}
}