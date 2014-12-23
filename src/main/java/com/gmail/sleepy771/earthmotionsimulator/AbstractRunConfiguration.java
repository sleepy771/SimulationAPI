package com.gmail.sleepy771.earthmotionsimulator;

import java.util.LinkedList;
import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;
import com.gmail.sleepy771.earthmotionsimulator.simulation.impl.SimulationFactory;

public abstract class AbstractRunConfiguration implements RunConfiguration {

	private SimulationSystem<?> system;
	private SimulationCondition runCondition;
	private SimulationExecutor executor;
	
	protected AbstractRunConfiguration() {
		beforeStart();
	}
	
	@Override
	public final SimulationSystem<?> getSystem() {
		if (system == null)
			system = createSystem();
		return system;
	}

	@Override
	public final SimulationExecutor getExecutor() {
		if (executor == null)
			executor = createExecutor();
		return executor;
	}

	@Override
	public final SimulationCondition getRunCondition() {
		if (runCondition == null)
			runCondition = createRunCondition();
		return runCondition;
	}
	
	@Override
	public final List<Simulation> getSimulations() {
		if (useFactory()) {
			List<Simulation> sims = new LinkedList<>();
			System.out.println("getting simulations list");
			while (getFactory().canCreate()) {
				System.out.println("creating simulation");
				sims.add(getFactory().createSimulation());
			}
			return sims;
		}
		return listSimulations();
	}
	
	protected abstract SimulationSystem<?> createSystem();
	
	protected abstract SimulationExecutor createExecutor();
	
	protected abstract SimulationCondition createRunCondition();
	
	protected abstract boolean useFactory();
	
	protected abstract SimulationFactory getFactory();
	
	protected abstract List<Simulation> listSimulations();

}
