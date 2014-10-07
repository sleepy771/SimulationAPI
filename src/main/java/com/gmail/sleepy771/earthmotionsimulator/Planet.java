package com.gmail.sleepy771.earthmotionsimulator;

import Jama.Matrix;

public class Planet implements Body {
	private final Matrix p, r;
	private final double m;
	private final int sgn;
	
	public Planet(int sgn, double mass, Matrix p, Matrix r) {
		this.r = r;
		this.m = mass;
		this.p = p;
		this.sgn = sgn;
	}
	
	public Planet(Planet p) {
		this(p.sgn, p.m, p.p, p.r);
	}
	
	public Planet(Planet planet, Matrix p, Matrix r) {
		this(planet.sgn, planet.m, p, r);
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
		return p.times(1./m);
	}

	@Override
	public double getMass() {
		return m;
	}
	
	public int signature() {
		return sgn;
	}
	
}
