package com.gmail.sleepy771.earthmotionsimulator.units;

public enum UnitPrefix {
	MILLI(1e-3), MICRO(1e-6), NANO(1e-9), PICO(1e-12), FEMTO(1e-15), KILO(1e3), MEGA(1e6), GIGA(1e9), TERA(1e12), PETA(1e15);
	private final double mul;
	
	UnitPrefix(double mul) {
		this.mul = mul;
	}
	
	public double getMultiplier() {
		return this.mul;
	}
	
	public double toBase(double value) {
		return this.mul * value;
	}
	
	public double fromBase(double value) {
		return value / this.mul;
	}
}
