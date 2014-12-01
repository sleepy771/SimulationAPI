package com.gmail.sleepy771.earthmotionsimulator.units;

public enum Units implements Unit {
	PER(UnitType.INV, 1., null),
	
	METER(UnitType.LENGTH, 1., null), INCH(UnitType.LENGTH, 0.254e-1, null), FOOT(UnitType.LENGTH, 0.3048, null), YARD(UnitType.LENGTH, 0.9144, null),
	MILE(UnitType.LENGTH, 1.60934e3, null), ASTRONOMICAL_UNIT(UnitType.LENGTH, 1.495978707e11, null), LIGHT_YEAR(UnitType.LENGTH, .94605284e16, null),
	FERMI(UnitType.LENGTH, 1e-15, null), ANGSTROM(UnitType.LENGTH, 1e-10, null),
	SECOND(UnitType.TIME, 1., null), HOUR(UnitType.TIME, 3600., null), MINUTE(UnitType.TIME, 60., null), DAY(UnitType.TIME, 3600. * 24, null),
	WEEK(UnitType.TIME, 7 * 3600. * 24, null), YEAR(UnitType.TIME, 365 * 24 * 3600., null),
	KELVIN(UnitType.TEMPERATURE, 1., null),
	CELSIUS(UnitType.TEMPERATURE, 1., new UnitConversionClosure() {
		@Override
		public double fromBase(double value) {
			return value - 273.15;
		}
		@Override
		public double toBase(double value) {
			return value + 273.15;
		}
	}),
	KILOGRAM(UnitType.MASS, 1., null), JOULE(UnitType.ENERGY, 1., null), ELECTRON_VLOT(UnitType.ENERGY, 1.602176565e-19, null);
	
	private final UnitType type;
	private final double mul;
	private final UnitConversionClosure closure;
	
	Units(UnitType t, double mul, UnitConversionClosure c) {
		type = t;
		this.mul = mul;
		this.closure = c;
	}

	@Override
	public UnitType getType() {
		return type;
	}

	@Override
	public boolean isSameAs(Unit u) {
		return u.getType() == type;
	}

	@Override
	public double getMultiplierTo(Unit u) {
		if (!this.isSameAs(u))
			throw new IllegalArgumentException("Wrong unit to convert to!");
		return getMultiplier()/u.getMultiplier();
	}

	@Override
	public double getMultiplier() {
		return mul;
	}

	@Override
	public boolean hasConversionClosure() {
		return closure != null;
	}

	@Override
	public UnitConversionClosure getConversion() {
		return closure;
	}
}
