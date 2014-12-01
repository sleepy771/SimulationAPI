package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Rotation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public class RotationR3 implements Rotation {

	private final static double EPSILON = 1e-12;
	private final static Matrix IDENTITY = Matrix.identity(3, 3);
	
	private Matrix rotMatrix;
	private Matrix axis;
	private double angle;
	
	@Override
	public Matrix getMatrix() {
		if (rotMatrix == null && axis != null) {
			rotMatrix = createRotationMatrix(angle, axis);
		}
		return rotMatrix;
	}

	@Override
	public void setMatrix(Matrix m) {
		if (!(m.getRowDimension() == 3 && m.getColumnDimension() == 3 && isOrthogonal(m) && isSpecial(m, EPSILON)))
			throw new IllegalArgumentException("Wrong matrix type matrix should be from SO(3)");
		rotMatrix = m;
		computeAngleAndAxis();
	}
	
	private static boolean isOrthogonal(Matrix m) {
		return isLessThanEpsilon(m.times(m.transpose()).minus(IDENTITY), EPSILON);
	}
	
	private static boolean isSpecial(Matrix m, double epsilon) {
		return Math.abs(m.det() - 1) < epsilon;
	}
	
	private static boolean isLessThanEpsilon(Matrix m, double epsilon) {
		int n = m.getColumnDimension();
		int o = m.getColumnDimension();
		for (int i=0; i<n; i++) {
			for (int j=0; j<o; j++) {
				if (m.get(i, j) >= epsilon)
					return false;
			}
		}
		return true;
	}
	
	private void computeAngleAndAxis() {
		EigenvalueDecomposition evd = new EigenvalueDecomposition(rotMatrix);
		double[] reEig = evd.getRealEigenvalues();
		double[] imEig = evd.getImagEigenvalues();
		for (int k=0; k<3; k++) {
			if (Math.abs(reEig[k] -1) < EPSILON) {
				axis = evd.getV().getMatrix(k, k, 0, 2);
				if (k == 2) {
					angle = Math.atan2(imEig[1], reEig[1]);
				} else {
					angle = Math.atan2(imEig[k+1], reEig[k+1]);
				}
				break;
			}
		}
	}

	@Override
	public Transformation leftTranslation(Transformation t) {
		return new AffineTransformationImpl(getMatrix()).leftTranslation(t);
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		return t.leftTranslation(this);
	}

	@Override
	public Matrix transform(Matrix v) {
		return getMatrix().times(v);
	}

	@Override
	public Matrix getAxis() {
		return this.axis;
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public void setAxis(Matrix n) {
		if (!(n.getRowDimension() == 3 && n.getColumnDimension() == 1))
			throw new IllegalArgumentException("Axis should be R3x1");
		this.axis = n;
		rotMatrix = null;
	}

	@Override
	public void setAngle(double angle) {
		this.angle = angle;
		rotMatrix = null;
	}
	
	private static Matrix createRotationMatrix(double a, Matrix n) {
		double ca = Math.cos(a);
		double sa = Math.sin(a);
		Matrix rot = new Matrix(3,3);
		for (int i=0; i<3; i++) {
			for (int j=0; j<3; j++) {
				double leviSin = 0.;
				for (int k=0; k<3; k++)
					leviSin += n.get(k, 0) * sa * leviCivite(i, j, k);
				double value = i==j? ca : 0.; 
				rot.set(i, j, value + (1 - ca) * n.get(i, 0) * n.get(j, 0) - leviSin);
			}
		}
		return rot;
	}
	
	private static double leviCivite(int i, int j, int k) {
		return Math.signum(j-i) * Math.signum(k - j) * Math.signum(k - i);
	}

	@Override
	public int dimension() {
		return 3;
	}

}
