package com.gmail.sleepy771.earthmotionsimulator;

import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public abstract class AbstractRunConfiguration implements RunConfiguration {

	private SimulationSystem<?> system;
	private SimulationExecutor executor;
	private SimulationCondition condition;
	
	@Override
	public final SimulationSystem<?> getSystem() {
		if (system == null)
			system = createSystem();
		return system;
	}
	
	public abstract SimulationSystem<?> createSystem();
	
	public abstract SimulationExecutor createExceutor();
	
	public abstract SimulationCondition createCondition();

	@Override
	public final SimulationExecutor getExecutor() {
		if (executor == null)
			executor = createExceutor();
		return executor;
	}

	@Override
	public final SimulationCondition getEndCondition() {
		if (condition == null)
			condition = createCondition();
		return condition;
	}
	
	protected final void releasAll() {
		this.system = null;
		this.condition = null;
		this.executor = null;
	}
}
