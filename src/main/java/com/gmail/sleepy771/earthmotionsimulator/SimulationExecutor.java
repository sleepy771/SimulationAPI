package com.gmail.sleepy771.earthmotionsimulator;

public interface SimulationExecutor {
	void runSimulation();
	
	SimulationSystem<?> getSystem();

	void stopSimulation();
	
	boolean isRunning();
}
