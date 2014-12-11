package com.gmail.sleepy771.earthmotionsimulator.simulation;

public interface SimulationExecutor {
	void runSimulation();
	
	SimulationSystem<?, ?> getSystem();
	
	void setSimulationSystem(SimulationSystem<?, ?> system);

	void stopSimulation();
	
	boolean isRunning();
	
	SimulationCondition getEndCondition();
	
	void setEndCondition(SimulationCondition cond);
}
