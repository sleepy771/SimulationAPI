package com.gmail.sleepy771.earthmotionsimulator;

import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.objects.Planet;
import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

import Jama.Matrix;

public class PlanetMotionSimulation implements Simulation {
	private Planet body;
	private final double G = 6.67e-11;
	private double dt;
	private SimulationSystem<Body> s; 
	
	public PlanetMotionSimulation(SimulationSystem<Body> s, Planet b) {
		this.s = s;
		body = b;
	}
	
	@Override
	public void makeStep() {
		List<Body> bodies = s.getUnits();
		Matrix f = new Matrix(1,3);
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
		s.getMovedUnits().add(body = new Planet(this.body, newMomentum, newPosistion));
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
