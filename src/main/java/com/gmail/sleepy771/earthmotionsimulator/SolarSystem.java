package com.gmail.sleepy771.earthmotionsimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SolarSystem implements SimulationSystem<Body> {
	
	private ESCondition endCond;
	private Iterator<Body> bodyIterator;
	private SimulationExecutor executor;
	private List<Body> bodies;
	private List<Body> movedBodies;
	
	public SolarSystem() {
	}

	@Override
	public void runSimulation() {
		getSimulationExecutor().runSimulation();
	}

	@Override
	public void setEndCondition(ESCondition cond) {
		this.endCond = cond;
	}

	@Override
	public ESCondition getEndCondition() {
		return endCond;
	}

	@Override
	public Simulation createSimualtion() {
		return new PlanetMotionSimulation(this, (Planet) getIterator().next());
	}

	@Override
	public boolean canCreateSimulation() {
		return getIterator().hasNext();
	}
	
	private Iterator<Body> getIterator() {
		if (bodyIterator == null) {
			bodyIterator = bodies.iterator();
		}
		return bodyIterator;
	}

	@Override
	public void setSimulationExecutor(SimulationExecutor exec) {
		if (getSimulationExecutor().isRunning())
			getSimulationExecutor().stopSimulation();
		bodyIterator = null;
		this.executor = exec;
	}

	@Override
	public SimulationExecutor getSimulationExecutor() {
		if (executor == null) {
			executor = new SimulationExecutor() {
				@Override
				public void stopSimulation() {
				}
				@Override
				public void runSimulation() {
				}
				@Override
				public boolean isRunning() {
					return false;
				}
				@Override
				public SimulationSystem<Body> getSystem() {
					return SolarSystem.this;
				}
			};
		}
		return executor;
	}

	@Override
	public void setUnits(List<Body> units) {
		bodies = units;
		movedBodies = null;
	}

	@Override
	public List<Body> getUnits() {
		return bodies;
	}

	@Override
	public List<Body> getMovedUnits() {
		if (movedBodies == null) 
			movedBodies = new ArrayList<>();
		return movedBodies;
	}

	@Override
	public void fireUpdate() {
		setUnits(getMovedUnits());
	}
}
