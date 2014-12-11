package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gmail.sleepy771.earthmotionsimulator.Record;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;

public class ParticleSystemRecord implements Record {
	
	private final Map<Object, Body> bodies;
	private final double time;
	
	public ParticleSystemRecord(List<Body> bodies, double time) {
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
