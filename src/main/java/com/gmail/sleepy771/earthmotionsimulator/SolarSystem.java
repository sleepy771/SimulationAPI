package com.gmail.sleepy771.earthmotionsimulator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.objects.Planet;
import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public class SolarSystem implements SimulationSystem<Body> {
	private Lock streamerLock;
	private Iterator<Body> bodyIterator;
	private List<Body> bodies;
	private List<Body> movedBodies;
	private Storage<SpaceSimulationRecord> storage;
	private double simulationTime;
	private double dt;
	
	public SolarSystem(double startTime, double dt, List<Body> bodies, Storage<SpaceSimulationRecord> storage) {
		this.storage = storage;
		this.bodies = new ArrayList<Body>(bodies);
		streamerLock = new ReentrantLock();
		simulationTime = startTime;
		this.dt = dt;
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

	@Override
	public void fireUpdate() {
		streamerLock.lock();
		try {
			bodies = movedBodies;
			movedBodies = null;
			storage.push(new SpaceSimulationRecord(bodies, simulationTime));
			simulationTime += dt;
		} finally {
			streamerLock.unlock();
		}
	}

	@Override
	public void flush() {
		bodyIterator = null;
		streamerLock.unlock();
		storage.flush();
		bodies.clear();
		movedBodies.clear();
		streamerLock = null;
	}
}
