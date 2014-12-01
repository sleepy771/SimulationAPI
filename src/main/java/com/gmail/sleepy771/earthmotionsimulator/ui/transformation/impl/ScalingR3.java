package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Scaling;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public class ScalingR3 implements Scaling {
	
	private final static Matrix IDENTITY = Matrix.identity(3, 3);
	private final static double EPSILON = 1e-12;
	
	private double scaling;
	
	public ScalingR3(double scaling) {
		this.scaling = scaling;
	}

	@Override
	public Matrix getMatrix() {
		return IDENTITY.times(scaling);
	}

	@Override
	public void setMatrix(Matrix m) {
		if (!(Math.abs(m.get(0, 0)) > 0. && isZero(m.minus(IDENTITY.times(m.get(0, 0))))))
			throw new IllegalArgumentException("Wrong scaling matrix");
		scaling = m.get(0, 0);
	}
	
	@Override
	public void setScaling(double value) {
		if (value == 0.)
			throw new IllegalArgumentException("Invalid Sacling Matrix");
		this.scaling = value;
	}
	
	private static boolean isZero(Matrix m) {
		for (int k=0; k<3; k++)
			for (int j=0; j<3; j++)
				if(Math.abs(m.get(k, j)) >= EPSILON)
					return false;
		return true;
	}

	@Override
	public Transformation leftTranslation(Transformation t) {
		return new AffineTransformationImpl(getMatrix()).leftTranslation(t);
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		return t.rightTranslation(this);
	}

	@Override
	public Matrix transform(Matrix v) {
		return v.times(scaling);
	}

	@Override
	public int dimension() {
		return 3;
	}

	@Override
	public double getScaling() {
		return scaling;
	}
}
