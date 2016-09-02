package com.johnmarkese.se450.networksvc.shortestpath;

import java.util.List;

import com.johnmarkese.se450.utils.ParameterValidationException;

public interface Vertex {
	public String getName();
	public List<Edge> getEdges();
	public void addEdge(Edge edge);
	public void removeEdge(Edge edge);
	public double getDistance();
	public double getDistanceModified();
	public void setDistance(double distance) throws ParameterValidationException;
	public void setDistanceModified(double d) throws ParameterValidationException;
	public Vertex getPrevious();
	public void setPrevious(Vertex previous) throws ShortestPathException;
}
