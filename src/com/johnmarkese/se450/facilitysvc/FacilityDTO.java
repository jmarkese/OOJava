package com.johnmarkese.se450.facilitysvc;

import java.util.HashMap;
import java.util.ArrayList;

public class FacilityDTO {
	public String name;
	public double cost;
	public int rate;
	public ArrayList<Integer> calendar;

	public FacilityDTO(String name, int rate, double cost,  ArrayList<Integer> calendar) {
		this.name = name;
		this.rate = rate;
		this.cost = cost;
		this.calendar = calendar;
	}

	public String toString(){
		return this.name;
	}
	
	@Override
	public int hashCode() {
		int hash = 1;
		int prime = 29;
		hash = prime * hash + name.hashCode();
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		FacilityDTO other = (FacilityDTO) obj;
		return name.equals(other.name);
	}

}
