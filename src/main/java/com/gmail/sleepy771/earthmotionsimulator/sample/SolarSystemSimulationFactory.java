package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.Iterator;

import com.gmail.sleepy771.earthmotionsimulator.RunConfiguration;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.impl.SimulationFactory;

public class SolarSystemSimulationFactory implements SimulationFactory {

	private Iterator<Body> particleIterator = null;
	private ParticleSystem system;
	private RunConfiguration cfg;

	public SolarSystemSimulationFactory(ParticleSystem ps, RunConfiguration r) {
		particleIterator = ps.getParticles().iterator();
		system = ps;
		cfg = r;
	}

	@Override
	public Simulation createSimulation() {
		if (!canCreate())
			throw new UnsupportedOperationException(
					"No simulation left in this system!");
		Planet p = (Planet) particleIterator.next();
		return new SolarSystemSimulation(system, p, cfg.getRunCondition(), 60);
	}

	@Override
	public boolean canCreate() {
		return particleIterator.hasNext();
	}

}
