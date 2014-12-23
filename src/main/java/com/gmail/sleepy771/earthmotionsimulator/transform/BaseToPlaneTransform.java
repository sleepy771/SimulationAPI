package com.gmail.sleepy771.earthmotionsimulator.transform;

import java.awt.geom.Point2D;

import Jama.Matrix;

public class BaseToPlaneTransform implements AffineTransformToPlane {

	private AffineTransform innerAf;
	
	public BaseToPlaneTransform(AffineTransform af) {
		innerAf = af;
	}
	
	@Override
	public Matrix transform(Matrix v) {
		Matrix w = innerAf.transform(v);
		return w.getMatrix(0, 1, 0, 0);
	}

	@Override
	public void setShift(Matrix s) {
		innerAf.setShift(s);
	}

	@Override
	public void setTransformation(Matrix m) {
		innerAf.setTransformation(m);
	}

	@Override
	public void setMatrix(Matrix af) {
		innerAf.setMatrix(af);
	}

	@Override
	public int getDomainDim() {
		return innerAf.getDomainDim();
	}

	@Override
	public int getCodomainDim() {
		return 2;
	}

	@Override
	public Matrix getTransformation() {
		return innerAf.getTransformation();
	}

	@Override
	public Matrix getShift() {
		return innerAf.getShift();
	}

	@Override
	public Matrix getAugumentedMatrix() {
		return innerAf.getAugumentedMatrix();
	}

	@Override
	public AffineTransform leftTranslation(AffineTransform af) {
		return innerAf.leftTranslation(af);
	}

	@Override
	public boolean isInvertible() {
		return false;
	}

	@Override
	public AffineTransform inverse() {
		throw new UnsupportedOperationException("This matrix is not inverible");
	}

	@Override
	public Point2D asPoint(Matrix v) {
		Matrix w = innerAf.transform(v);
		return new Point2D.Double(w.get(0, 0), w.get(1, 0));
	}

}
