package com.gmail.sleepy771.earthmotionsimulator.sample;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;

import Jama.Matrix;

public class Planet implements Body {
	private final Matrix p, r;
	private final double m;
	private final Object id;

	public Planet(Object id, double mass, double[] v, double[] r) {
		this(id, mass, new Matrix(v, 3).timesEquals(mass), new Matrix(r, 3));
	}

	public Planet(Object id, double mass, Matrix p, Matrix r) {
		this.r = r;
		this.m = mass;
		this.p = p;
		this.id = id;
	}

	public Planet(Planet p) {
		this(p.id, p.m, p.p, p.r);
	}

	public Planet(Planet planet, Matrix p, Matrix r) {
		this(planet.id, planet.m, p, r);
	}

	@Override
	public Matrix getMomentum() {
		return p;
	}

	@Override
	public Matrix getPosition() {
		return r;
	}

	@Override
	public Matrix getSpeed() {
		return p.times(1. / m);
	}

	@Override
	public double getMass() {
		return m;
	}

	@Override
	public Object getId() {
		return id;
	}
}
