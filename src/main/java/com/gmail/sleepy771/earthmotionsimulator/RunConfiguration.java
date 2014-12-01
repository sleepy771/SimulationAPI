package com.gmail.sleepy771.earthmotionsimulator;

import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public interface RunConfiguration {
	SimulationSystem<?> getSystem();
	
	SimulationExecutor getExecutor();
	
	SimulationCondition getEndCondition();
	
	void beforeStart();
	
	void afterStop();
}
