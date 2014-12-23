package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;

import Jama.Matrix;

public class SolarSystemSimulation implements Simulation {
	private Planet body;
	private final double G = 6.67384e-11;
	private double dt;
	private ParticleSystem system;
	
	private final SimulationCondition condition;
	
	public SolarSystemSimulation(ParticleSystem s, Planet p, SimulationCondition c, double dt) {
		system = s;
		body = p;
		condition = c;
		this.dt = dt;
	}
	
	@Override
	public void nextStep() {
		if (!condition.satisfies())
			throw new UnsupportedOperationException("Simulation can not perform next step");
		List<Body> bodies = system.getParticles();
		Matrix f = new Matrix(3,1);
		Matrix newPosistion = body.getPosition().plus(body.getSpeed().times(dt));
		for (Body otherBody : bodies) {
			if (!body.getId().equals(otherBody.getId())) {
				Matrix dir = otherBody.getPosition().minus(body.getPosition());
				double norm = dir.normF();
				dir.timesEquals(1./norm);
				f.plusEquals(dir.times(G * otherBody.getMass() * body.getMass() / (norm * norm)));
			}
		}
		Matrix newMomentum = body.getMomentum().plus(f.times(dt));
		system.cacheParticle(body = new Planet(this.body, newMomentum, newPosistion));
	}

	@Override
	public boolean canMakeNextStep() {
		return condition.satisfies();
	}

}
