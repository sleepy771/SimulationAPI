package com.gmail.sleepy771.earthmotionsimulator.transform;

import Jama.Matrix;

public class AffineTransformWrapper implements IAffineTransformWrapper {

	private AffineTransform innerAf;
	
	public AffineTransformWrapper(AffineTransform af) {
		innerAf = af;
	}
	
	public AffineTransformWrapper() {
		innerAf = null;
	}
	
	public void setAffineTransform(AffineTransform af) {
		innerAf = af;
	}
	
	public AffineTransform getAffineTransform() {
		return innerAf;
	}
	
	@Override
	public Matrix transform(Matrix v) {
		return innerAf.transform(v);
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
		return innerAf.getCodomainDim();
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
		return innerAf.isInvertible();
	}

	@Override
	public AffineTransform inverse() {
		return innerAf.inverse();
	}
	
	public boolean hasInnerTransform() {
		return innerAf != null;
	}

}
