package com.gmail.sleepy771.earthmotionsimulator.simulation.impl;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;

public interface SimulationFactory {
	Simulation createSimulation();
	
	boolean canCreate();
}
