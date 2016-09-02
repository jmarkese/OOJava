package com.johnmarkese.se450.networksvc.shortestpath;

public interface WeightModifier {

	public double modify(double weight);
	public double unModify(double weight);

}
