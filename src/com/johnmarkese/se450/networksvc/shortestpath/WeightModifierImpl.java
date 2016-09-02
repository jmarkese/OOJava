package com.johnmarkese.se450.networksvc.shortestpath;

import java.util.ArrayList;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class WeightModifierImpl implements WeightModifier {
	private double hours;// = 8.0;
	private double mph;// = 50.0;

	public WeightModifierImpl(ArrayList<Double> conditions) throws ParameterValidationException {
		if (conditions.size() > 2) {
			throw new ParameterValidationException("There are too many conditions for this weight modifier");		
		} else if (conditions.size() < 2) {
				throw new ParameterValidationException("There are not enough conditions for this weight modifier");		
		}
		this.setHours(conditions.get(0));
		this.setMph(conditions.get(1));
	}

	public WeightModifierImpl() { }

	@Override
	public double modify(double weight) {
		return weight * (1 / (hours * mph));
	}
	
	@Override
	public double unModify(double weight) {
		return weight * (hours * mph);
	}

	private void setHours(double hours) throws ParameterValidationException {
		if(!Double.isFinite(hours) || hours < 0)
			throw new ParameterValidationException("WeightModifierImpl hours must be a non-negative finite number");
		this.hours = hours;
	}

	private void setMph(double mph) throws ParameterValidationException {
		if(!Double.isFinite(mph) || mph < 0)
			throw new ParameterValidationException("WeightModifierImpl mph must be a non-negative finite number");
		this.mph = mph;
	}
}
