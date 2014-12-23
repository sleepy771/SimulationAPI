package com.gmail.sleepy771.earthmotionsimulator.units;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface Unit {
	
	UnitType getType();
	
	boolean isSameAs(Unit u);
	
	double getMultiplierTo(Unit u);
	
	double getMultiplier();
	
	boolean hasConversionClosure();
	
	UnitConversionClosure getConversion();
	
	abstract static class AbstractUnit implements Unit {
		@Override
		public final double getMultiplierTo(Unit u) {
			if (!this.isSameAs(u))
				throw new IllegalArgumentException("Wrong unit type");
			return getMultiplier()/u.getMultiplier();
		}
	}
	
	public static class Composed extends AbstractUnit implements Unit {
		
		public static final Composed KILOGRAM_METER_PER_SECOND = new Composed(Units.KILOGRAM, Units.METER, new Unit.Inverse(Units.SECOND));
		public static final Composed KILOGRAM_ASTRONOMICAL_UNIT_PER_DAY = new Composed(Units.KILOGRAM, Units.ASTRONOMICAL_UNIT, new Unit.Inverse(Units.DAY));
		public static final Composed METER_PER_SECOND = new Composed(Units.METER, new Unit.Inverse(Units.SECOND));
		public static final Composed ASTRONOMICAL_UNIT_PER_DAY = new Composed(Units.ASTRONOMICAL_UNIT, new Unit.Inverse(Units.DAY));

		private final List<Unit> units;
		private double multiplier = -1.;
		
		private Composed(Unit... units) {
			this.units = Collections.unmodifiableList(Arrays.asList(units));
		}
		
		public double getMultiplier() {
			if (multiplier < 0.) {
				computeMultiplier();
			}
			return multiplier;
		}
		
		private void computeMultiplier() {
			this.multiplier = 1.;
			// toto je blbost lebo tu ratam s tym ze [x]->a*[x] == [y]
			for(Unit u : this.units) {
				multiplier *= u.getMultiplier();
			}
		}

		@Override
		public UnitType getType() {
			return UnitType.COMPOSED;
		}

		@Override
		public boolean isSameAs(Unit u) {
			if (u.getType() != UnitType.COMPOSED)
				return false;
			Composed c = Composed.class.cast(u);
			if (c.units.size() != units.size())
				return false;
			for (int k=0; k < units.size(); k++) {
				if (! units.get(k).isSameAs(c.units.get(k)))
					return false;
			}
			return true;
		}

		@Override
		public boolean hasConversionClosure() {
			return false;
		}

		@Override
		public UnitConversionClosure getConversion() {
			return null;
		}
		
	}
	
	public static class Inverse extends AbstractUnit implements Unit {

		private final Unit unit;
		private UnitConversionClosure closure = null;
		
		public Inverse(Unit u) {
			unit = u;
		}
		
		@Override
		public UnitType getType() {
			return UnitType.INV;
		}

		@Override
		public boolean isSameAs(Unit u) {
			if (u.getType() != UnitType.INV)
				return false;
			Inverse perUnit = Inverse.class.cast(u);
			return this.unit.isSameAs(perUnit.unit);
		}

		@Override
		public double getMultiplier() {
			return 1./unit.getMultiplier();
		}

		@Override
		public boolean hasConversionClosure() {
			return unit.hasConversionClosure();
		}

		@Override
		public UnitConversionClosure getConversion() {
			if (unit.hasConversionClosure() && closure == null) {
				closure = new UnitConversionClosure() {
					private UnitConversionClosure baseConv = unit.getConversion();
					@Override
					public double toBase(double value) {
						return 1./baseConv.toBase(value);
					}
					
					@Override
					public double fromBase(double value) {
						return 1./baseConv.fromBase(value);
					}
				};
			}
			return closure;
		}
		
	}
}
