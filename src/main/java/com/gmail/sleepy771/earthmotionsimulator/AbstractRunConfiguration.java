package com.gmail.sleepy771.earthmotionsimulator;

import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public abstract class AbstractRunConfiguration<SYSTYPE extends SystemUnit, STORTYPE extends Record> implements RunConfiguration {

	private SimulationSystem<SYSTYPE> system;
	private SimulationExecutor executor;
	private SimulationCondition condition;
	private Storage<STORTYPE> storage;
	
	@Override
	public final SimulationSystem<SYSTYPE> getSystem() {
		if (system == null)
			system = createSystem();
		return system;
	}
	
	protected abstract SimulationSystem<SYSTYPE> createSystem();
	
	protected abstract SimulationExecutor createExceutor();
	
	protected abstract SimulationCondition createCondition();

	protected abstract Storage<STORTYPE> createStorage();
	
	@Override
	public final SimulationExecutor getExecutor() {
		if (executor == null)
			executor = createExceutor();
		executor.setSimulationSystem(getSystem());
		executor.setEndCondition(getEndCondition());
		return executor;
	}

	@Override
	public final SimulationCondition getEndCondition() {
		if (condition == null)
			condition = createCondition();
		return condition;
	}
	
	@Override
	public final Storage<STORTYPE> getStorage() {
		if (storage == null)
			storage = createStorage();
		return storage;
	}
	
	protected final void releasAll() {
		this.system = null;
		this.condition = null;
		this.executor = null;
	}
}
