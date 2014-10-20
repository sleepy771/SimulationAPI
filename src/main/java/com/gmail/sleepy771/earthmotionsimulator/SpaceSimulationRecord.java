package com.gmail.sleepy771.earthmotionsimulator;

import java.util.List;

public class SpaceSimulationRecord implements Response {
	
	private final List<Body> bodies;
	private final double time;
	
	public SpaceSimulationRecord(List<Body> bodies, double time) {
		this.bodies = bodies;
		this.time = time;
	}
	
	public List<Body> getBodies() {
		return this.bodies;
	}
	
	public double getTime() {
		return time;
	}
}
