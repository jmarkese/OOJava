package com.johnmarkese.se450.networksvc.shortestpath;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class EdgeImpl implements Edge {
	private Vertex to;
	private double weight;
	private double modifiedWeight;
	private WeightModifier wm;

	public EdgeImpl(Vertex to, double weight) throws ParameterValidationException, ShortestPathException {
		setTo(to);
		setWeight(weight);
	}
	
	public EdgeImpl(Vertex to, double weight, WeightModifier wm) throws ParameterValidationException, ShortestPathException {
		setTo(to);
		setWeightModifier(wm);
		setWeight(weight);
	}

	private void setWeightModifier(WeightModifier wm) throws ParameterValidationException {
		if(wm == null){
			throw new ParameterValidationException("The WeightedEdge to must be set.");
		}
		this.wm = wm;
	}

	@Override
	public Vertex to() {
		return this.to;
	}

	@Override
	public String getTo() {
		return this.to.getName();
	}

	@Override
	public double getWeight() {
		return this.weight;
	}
	
	@Override
	public double getWeightModified() {
		return this.modifiedWeight;
	}

	private void setTo(Vertex to) throws ParameterValidationException {
		if (to == null) {
			throw new ParameterValidationException("The WeightedEdge to must be set.");
		}
		this.to = to;
	}

	private void setWeight(double weight) throws ParameterValidationException, ShortestPathException {
		if (weight < 0.0) {
			throw new ParameterValidationException("The WeightedEdge weight cannot be negative.");
		}
		this.weight = weight;
		this.modifiedWeight = (this.wm != null) ? this.wm.modify(weight) : weight;
	}
	
	public void modifyWeight(WeightModifier condition) throws ShortestPathException, ParameterValidationException{
		if(condition != null){
			this.modifiedWeight = condition.modify(this.weight); //WeightModifierFactory.getInstance().modifier().modify(baseWeight);	
		} else {
			throw new ParameterValidationException("The WeightModifier condition cannot be null.");
		}
	}

	@Override
	public String toString() {
		return "Edge to " + getTo().toString() + " in " + new Double(getWeight()).toString();
	}

}
