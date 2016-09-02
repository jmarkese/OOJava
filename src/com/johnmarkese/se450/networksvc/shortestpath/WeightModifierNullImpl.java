package com.johnmarkese.se450.networksvc.shortestpath;

public class WeightModifierNullImpl implements WeightModifier {

	@Override
	public double modify(double weight) {
		return weight;
	}

	@Override
	public double unModify(double weight) {
		// TODO Auto-generated method stub
		return weight;
	}

}
