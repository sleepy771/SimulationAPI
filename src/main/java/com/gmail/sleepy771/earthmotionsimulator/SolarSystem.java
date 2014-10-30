package com.gmail.sleepy771.earthmotionsimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SolarSystem implements SimulationSystem<Body> {
	private Lock streamerLock;
	private Condition hasNew;
	private Iterator<Body> bodyIterator;
	private List<Body> bodies;
	private List<Body> movedBodies;
	private DataServer<Void, SpaceSimulationRecord> server;
	private Thread streamerThread;
	private Runnable pusherRunnable;
	private double simulationTime;
	private double dt;
	
	public SolarSystem(double startTime, double dt, List<Body> bodies, DataServer<Void, SpaceSimulationRecord> server, ESCondition endCondition) {
		this.server = server;
		this.bodies = new ArrayList<Body>(bodies);
		streamerLock = new ReentrantLock();
		hasNew = streamerLock.newCondition();
		simulationTime = startTime;
		this.dt = dt;
		pusherRunnable = new Runnable() {
			@Override
			public void run() {
				try {
					while (SolarSystem.this.server.isListening()) {
						streamerLock.lock();
						try {
							hasNew.await();
							SolarSystem.this.server.insertRecord(new SpaceSimulationRecord(getUnits(), getSimulationTime()));
						} finally {
							streamerLock.unlock();
						}
					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
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
	public void setUnits(List<Body> units) {
		streamerLock.lock();
		try {
			bodies = units;
			movedBodies = null;
		} finally {
			streamerLock.unlock();
		}
	}

	@Override
	public List<Body> getUnits() {
		streamerLock.lock();
		try {
			return bodies;
		} finally {
			streamerLock.unlock();
		}
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
			bodies = movedBodies;
			movedBodies = null;
			simulationTime += dt;
			hasNew.signal();
		} finally {
			streamerLock.unlock();
		}
	}
}
