package com.gmail.sleepy771.earthmotionsimulator.sample;

import org.joda.time.DateTime;

import com.gmail.sleepy771.earthmotionsimulator.simulation.AbstractSimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;

public class SolarSystemSimualtionEndCondtion extends
		AbstractSimulationCondition implements SimulationCondition {

	private final ParticleSystem system;
	private final double endDate;
	
	public SolarSystemSimualtionEndCondtion(ParticleSystem s, DateTime e) {
		system = s;
		endDate = e.getMillis();
	}
	
	@Override
	public boolean condition() {
		return system.getCurrentTime() < endDate;
	}

}
