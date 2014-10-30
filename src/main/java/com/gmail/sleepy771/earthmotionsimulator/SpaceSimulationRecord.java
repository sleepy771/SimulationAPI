package com.gmail.sleepy771.earthmotionsimulator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceSimulationRecord implements Response {
	
	private final Map<Object, Body> bodies;
	private final double time;
	
	public SpaceSimulationRecord(List<Body> bodies, double time) {
		this.bodies = new HashMap<>();
		for (Body b : bodies) {
			this.bodies.put(b.getId(), b);
		}
		this.time = time;
	}
	
	public Map<Object, Body> getBodies() {
		return this.bodies;
	}
	
	public double getTime() {
		return time;
	}
}
