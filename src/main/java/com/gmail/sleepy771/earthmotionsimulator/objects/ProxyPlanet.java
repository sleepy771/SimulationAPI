package com.gmail.sleepy771.earthmotionsimulator.objects;

import com.gmail.sleepy771.earthmotionsimulator.units.Amount;
import com.gmail.sleepy771.earthmotionsimulator.units.Unit;

import Jama.Matrix;

public class ProxyPlanet {

	private final Unit pUnit;
	private final Unit rUnit;
	private final Unit mUnit;
	private final Planet planet;

	public ProxyPlanet(Unit unitP, Unit unitR, Unit unitM, Planet p) {
		planet = p;
		mUnit = unitM;
		pUnit = unitP;
		rUnit = unitR;
	}

	public Amount<Matrix> getMomentum() {
		return new Amount.MatrixAmount(pUnit, planet.getMomentum());
	}

	public Amount<Matrix> getPosition() {
		return new Amount.MatrixAmount(rUnit, planet.getPosition());
	}

	public Amount<Double> getMass() {
		return new Amount.NumberAmount(mUnit, planet.getMass());
	}

	public ProxyPlanet convertUnits(Unit unitP, Unit unitR, Unit unitM) {
		return new ProxyPlanet(unitP, unitR, unitM, new Planet(planet.getId(),
				getMass().convertTo(unitM).getValue(), getMomentum().convertTo(
						unitP).getValue(), getPosition().convertTo(unitR)
						.getValue()));
	}

	public Planet getPlanet() {
		return planet;
	}
}
