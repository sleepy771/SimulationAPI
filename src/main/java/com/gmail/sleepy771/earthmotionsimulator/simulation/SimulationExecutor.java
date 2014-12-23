package com.gmail.sleepy771.earthmotionsimulator.simulation;

import java.util.List;

public interface SimulationExecutor {
	
	void start();
	
	void add(Simulation s);
	
	boolean isRunning(Simulation s);
	
	boolean isRunning();

	void terminateAll();
	
	void terminate(Simulation s);
	
	List<Simulation> listAll();
}
