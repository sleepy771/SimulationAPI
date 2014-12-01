package com.gmail.sleepy771.earthmotionsimulator.units;

import Jama.Matrix;

public interface Amount<T> {
	T getValue();
	
	void setValue(T value);
	
	Unit getUnit();
	
	Amount<T> convertTo(Unit u);
	
	public static class MatrixAmount implements Amount<Matrix> {

		private Matrix value;
		private final Unit unit;
		
		public MatrixAmount(Unit u, Matrix value) {
			this(u);
			setValue(value);
		}
		
		public MatrixAmount(Unit u) {
			this.unit = u;
		}
		
		@Override
		public Matrix getValue() {
			return value;
		}

		@Override
		public void setValue(Matrix value) {
			this.value = value;
		}

		@Override
		public Unit getUnit() {
			return unit;
		}

		@Override
		public Amount<Matrix> convertTo(Unit u) {
			double mul = unit.getMultiplierTo(u);
			return new MatrixAmount(u, value.times(mul));
		}
	}
	
	public static class NumberAmount implements Amount<Double> {

		private double value;
		private final Unit unit;
		
		public NumberAmount(Unit u, double value) {
			this.unit = u;
			this.value = value;
		}
		
		public NumberAmount(Unit u) {
			this(u, 0.);
		}
		
		@Override
		public Double getValue() {
			return value;
		}

		@Override
		public void setValue(Double value) {
			this.value = value.doubleValue();
		}

		@Override
		public Unit getUnit() {
			return unit;
		}

		@Override
		public Amount<Double> convertTo(Unit u) {
			return new NumberAmount(u, unit.getMultiplierTo(u) * this.value);
		}
		
	}
}
