package com.johnmarkese.se450.networksvc.shortestpath;

import java.util.List;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class VertexNullImpl implements Vertex {

	public VertexNullImpl(String name) throws ParameterValidationException {}
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Edge> getEdges() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public double getDistance() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setDistance(double distance) throws ParameterValidationException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Vertex getPrevious() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setPrevious(Vertex previous) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeEdge(Edge edge) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public double getDistanceModified() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setDistanceModified(double d) throws ParameterValidationException {
		// TODO Auto-generated method stub
		
	}

}
