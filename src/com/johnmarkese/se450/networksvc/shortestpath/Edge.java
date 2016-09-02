package com.johnmarkese.se450.networksvc.shortestpath;

public interface Edge {

	public Vertex to();
	public double getWeight();
	public double getWeightModified();
	public String getTo();
}