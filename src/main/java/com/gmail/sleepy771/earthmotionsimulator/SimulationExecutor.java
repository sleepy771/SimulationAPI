package com.gmail.sleepy771.earthmotionsimulator;

public interface SimulationExecutor {
	void runSimulation();
	
	void setSystem(System s);
	
	System getSystem();
}
