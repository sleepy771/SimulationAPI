package com.gmail.sleepy771.earthmotionsimulator;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;

public class RunSimulation {
	
	private RunConfiguration configuration;
	private SimulationExecutor executor;
	
	
	public RunSimulation(RunConfiguration conf) {
		this.configuration = conf;
	}
	
	public void runSimulation() {
		executor = configuration.getExecutor();
		for (Simulation s : configuration.getSimulations()) {
			executor.add(s);
		}
		executor.start();
	}
	
	public void stopSimulation() {
		executor.terminateAll();
		configuration.afterStop();
	}
}
