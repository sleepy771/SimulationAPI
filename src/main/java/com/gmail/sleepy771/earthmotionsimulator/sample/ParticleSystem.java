package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.UnmodifiableIterator;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.recordstreaming.RecordSource;
import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public class ParticleSystem implements SimulationSystem<Body, ParticleSystemRecord> {
	private Iterator<Body> bodyIterator;
	private List<Body> bodies;
	private List<Body> cache;
	private double simulationTime;
	private double dt;
	
	private RecordSource<ParticleSystemRecord> source;

	public ParticleSystem(double startTime, double dt, List<Body> bodies,
			Storage<ParticleSystemRecord> storage) {
		this.bodies = new LinkedList<Body>(bodies);
		simulationTime = startTime;
		this.dt = dt;
	}

	public double getSimulationTime() {
		return simulationTime;
	}

	@Override
	public Simulation createSimualtion() {
		Planet p = (Planet) getIterator().next();
		return new PlanetMotionSimulation(this, p);
	}

	@Override
	public boolean canCreateSimulation() {
		return getIterator().hasNext();
	}

	private Iterator<Body> getIterator() {
		if (bodyIterator == null) {
			bodyIterator = new UnmodifiableIterator<Body>(bodies.iterator());
		}
		return bodyIterator;
	}

	@Override
	public void setUnits(List<Body> units) {
		bodies = units;
	}

	@Override
	public List<Body> getUnits() {
		return bodies;
	}

	@Override
	public List<Body> getCache() {
		createNewCache();
		return cache;
	}
	
	private void createNewCache() {
		if (cache == null)
			cache = new LinkedList<>();
	}

	@Override
	public void performUpdate() {
		setUnits(getCache());
		source.pushRecord(createRecord());
		cache = null;
		simulationTime += dt;
	}

	@Override
	public void flush() {
		bodyIterator = null;
		bodies.clear();
		cache.clear();
	}

	@Override
	public ParticleSystemRecord createRecord() {
		return new ParticleSystemRecord(getUnits(), getSimulationTime());
	}

	@Override
	public void addToCache(Body u) {
		getCache().add(u);
	}
}
