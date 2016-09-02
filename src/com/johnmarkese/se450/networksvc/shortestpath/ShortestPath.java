package com.johnmarkese.se450.networksvc.shortestpath;

import java.util.PriorityQueue;

import com.johnmarkese.se450.utils.ParameterValidationException;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ShortestPath {
	private PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>();
	private double distance;
	private double modifiedDistance;
	private List<String> path;
	private WeightModifier wm;

	public ShortestPath(Vertex v, Vertex w, Collection<Vertex> vertices)
			throws ParameterValidationException, ShortestPathException {
		if (v == null || w == null) {
			throw new ShortestPathException("Both vertices source and target must be valid");
		} else if (vertices == null) {
			throw new ShortestPathException("The Collection<Vertex> vertices cannot be null ");
		}
		processPath(v, w, vertices);
	}
	
	public ShortestPath(Vertex v, Vertex w, Collection<Vertex> vertices, WeightModifier wm)
			throws ParameterValidationException, ShortestPathException {
		if (v == null && w == null) {
			throw new ParameterValidationException("Both vertices v and w must be set.");
		}
		this.setWeightModifier(wm);
		this.processPath(v, w, vertices);
	}

	private void setWeightModifier(WeightModifier wm) throws ParameterValidationException {
		if (wm == null) {
			throw new ParameterValidationException("WeightModifier must be initialized.");
		} else {
			this.wm = wm;
		}
	}

	private void processPath(Vertex v, Vertex w, Collection<Vertex> vertices)
			throws ParameterValidationException, ShortestPathException {
		ArrayList<String> path = new ArrayList<String>();
		this.resetGraph(vertices);
		this.paths(v);
		
		v.setDistance(0.0);
		Vertex vertex = w;
		while (vertex != null){
			path.add(vertex.getName());
			vertex = vertex.getPrevious();
		}
		
		this.distance = w.getDistance();
		this.modifiedDistance = this.modifyWeight(this.distance);

		Collections.reverse(path);
		this.path = path;
	}

	private void paths(Vertex source) throws ParameterValidationException, ShortestPathException {
		source.setDistance(0.0);
		this.pq.add(source);
		while (!this.pq.isEmpty()) {
			Vertex w = this.pq.poll();
			for (Edge e : w.getEdges()) {
				Vertex v = e.to();
				double weight = e.getWeight();
				double dist = w.getDistance() + weight;
				if (dist < v.getDistance()) {
					this.pq.remove(v);
					v.setDistance(dist);
					v.setPrevious(w);
					this.pq.add(v);
				}
			}
		}
	}

	private void resetGraph(Collection<Vertex> graph) throws ShortestPathException, ParameterValidationException{
		for (Vertex v : graph) {
			v.setDistance(Double.POSITIVE_INFINITY);
			v.setPrevious(null);
		}
		pq = new PriorityQueue<Vertex>();
	}

	public double getDistance() {
		return this.distance;
	}

	public double getDistanceModified() {
		return this.modifiedDistance;
	}

	private double modifyWeight(double w) {
		if (this.wm != null) {
			return wm.modify(w); 
		} else {
			return w;
		}
	}

	public List<String> getPath() {
		return path;
	}

}