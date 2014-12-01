package com.gmail.sleepy771.earthmotionsimulator;

import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public class RunSimulation {
	
	private RunConfiguration configuration;
	private SimulationSystem<?> system;
	private SimulationExecutor executor;
	
	
	public RunSimulation(RunConfiguration conf) {
		this.configuration = conf;
		configuration.beforeStart();
	}
	
	public void runSimulation() {
		system = configuration.getSystem();
		executor = configuration.getExecutor();
		executor.setSimulationSystem(system);
		executor.runSimulation();
	}
	
	public void stopSimulation() {
		executor.stopSimulation();
		configuration.afterStop();
	}
}
