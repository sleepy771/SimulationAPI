package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;
import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

import Jama.Matrix;

public class PlanetMotionSimulation implements Simulation {
	private Planet body;
	private final double G = 6.67e-11;
	private double dt;
	private SimulationSystem<Body, ParticleSystemRecord> s; 
	
	public PlanetMotionSimulation(SimulationSystem<Body, ParticleSystemRecord> s, Planet b) {
		this.s = s;
		body = b;
	}
	
	@Override
	public SystemUnit makeStep() {
		List<Body> bodies = s.getUnits();
		Matrix f = new Matrix(3,1);
		Matrix newPosistion = body.getPosition().plus(body.getSpeed().times(dt));
		for (Body otherBody : bodies) {
			if (this.body != otherBody) {
				Matrix dir = otherBody.getPosition().minus(body.getPosition());
				double norm = dir.normF();
				dir.timesEquals(1./norm);
				f.plusEquals(dir.times(G * otherBody.getMass() * body.getMass() / (norm * norm)));
			}
		}
		Matrix newMomentum = body.getMomentum().plus(f.times(dt));
		body = new Planet(this.body, newMomentum, newPosistion);
		return body;
	}

	@Override
	public void setTimeDelta(double time) {
		dt = time;
	}

	@Override
	public double getTimeDelta() {
		return dt;
	}

}
