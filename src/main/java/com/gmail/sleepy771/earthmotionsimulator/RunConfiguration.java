package com.gmail.sleepy771.earthmotionsimulator;

import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public interface RunConfiguration {
	SimulationSystem<?> getSystem();
	
	SimulationExecutor getExecutor();
	
	SimulationCondition getRunCondition();
	
	List<Simulation> getSimulations();
	
	void beforeStart();
	
	void afterStop();
}
