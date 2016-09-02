package com.johnmarkese.se450.networksvc.shortestpath;

import com.johnmarkese.se450.utils.ParameterValidationException;

public class VertexFactory {
	private VertexFactory(){}
	
	public static Vertex vertex(String name) throws ParameterValidationException {
		if (name.length() > 0) {
			return new VertexImpl(name);
		} else {
			throw new ParameterValidationException("You must enter a name for your Vertex");
		}
	}

	public static Vertex vertex(Vertexable vertex) throws ParameterValidationException {
		if (vertex.vertexName().isEmpty()) {
			return new VertexNullImpl(vertex.vertexName());
		} else {
			throw new ParameterValidationException("You must have a name and type for your Vertex");
		}
	}
}
