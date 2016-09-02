package com.johnmarkese.se450.networksvc.shortestpath;

import java.util.ArrayList;
import java.util.List;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class VertexImpl implements Vertex, Comparable<Vertex> {

	private final String name;
	private ArrayList<Edge> edges = new ArrayList<Edge>();
	private double minDistance = Double.POSITIVE_INFINITY;
	private double minDistanceMod = Double.POSITIVE_INFINITY;
	private Vertex previous;

	public VertexImpl(String argName) {
		name = argName;
	}

	@Override
	public int hashCode() {
		int hash = 1;
		int prime = 29;
		hash = prime * hash + getName().hashCode(); // TODO double check this
													// hash
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		Vertex that = (Vertex) obj;
		if (this == that || this.getName().equals(that.getName())) {
			return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public int compareTo(Vertex other) {
		return Double.compare(this.getDistance(), other.getDistance());
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public List<Edge> getEdges() {
		return edges;
	}

	@Override
	public double getDistance() {
		return this.minDistance;
	}
	
	@Override
	public double getDistanceModified() {
		return this.minDistanceMod;
	}

	@Override
	public void setDistance(double distance) throws ParameterValidationException {
		this.minDistance = distance;
	}
	
	@Override
	public void setDistanceModified(double distance) throws ParameterValidationException {
		// TODO Auto-generated method stub
		this.minDistanceMod = distance;
	}


	@Override
	public void addEdge(Edge edge) {
		this.edges.add(edge);
	}

	@Override
	public void removeEdge(Edge edge) {
		int index = this.edges.indexOf(edge);
		this.edges.remove(index);
	}

	@Override
	public Vertex getPrevious() {
		return this.previous;
	}

	@Override
	public void setPrevious(Vertex previous) throws ShortestPathException {
		if (previous == null) {
			//throw new ShortestPathException("Previous cannot be null");
		}
		this.previous = previous;
	}

}
