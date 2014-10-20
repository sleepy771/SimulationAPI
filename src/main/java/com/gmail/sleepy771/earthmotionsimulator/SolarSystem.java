package com.gmail.sleepy771.earthmotionsimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolarSystem implements SimulationSystem<Body> {
	
	private ESCondition endCond;
	private Lock streamerLock;
	private Iterator<Body> bodyIterator;
	private SimulationExecutor executor;
	private List<Body> bodies;
	private List<Body> movedBodies;
	private DataServer<Void, SpaceSimulationRecord> server;
	private Thread streamerThread;
	private Runnable pusherRunnable;
	private double simulationTime;
	private double dt;
	
	public SolarSystem(double startTime, double dt, List<Body> bodies, SimulationExecutor simExec, DataServer<Void, SpaceSimulationRecord> server, ESCondition endCondition) {
		this.endCond = endCondition;
		this.executor = simExec;
		this.server = server;
		this.bodies = new ArrayList<Body>(bodies);
		streamerLock = new ReentrantLock();
		simulationTime = startTime;
		this.dt = dt;
		pusherRunnable = new Runnable() {
			@Override
			public void run() {
				while (!SolarSystem.this.getEndCondition().satisfies()) {
					SolarSystem.this.server.insertRecord(new SpaceSimulationRecord(getUnits(), getSimulationTime()));
				}
			}
		};
	}
	
	public double getSimulationTime() {
		streamerLock.lock();
		try {
			return simulationTime;
		} finally {
			streamerLock.unlock();
		}
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
		if (bodyIterator == null || !bodyIterator.hasNext()) {
			bodyIterator = new UnmodifiableIterator<Body>(bodies.iterator());
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
	
	public Thread getStreamerThread() {
		if (streamerThread != null || !streamerThread.isAlive())
			streamerThread = new Thread(pusherRunnable);
		return streamerThread;
	}

	@Override
	public void fireUpdate() {
		streamerLock.lock();
		try {
			setUnits(getMovedUnits());
			simulationTime += dt;
		} finally {
			streamerLock.unlock();
		}
	}
}
