package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.geom.Point2D;

import Jama.Matrix;

public class SurfaceTrans implements TransformationToSurface {

	private Matrix n;
	private Matrix shift;
	private double angle;
	private double scaling;
	private Matrix roR;
	
	@Override
	public Matrix transform(Matrix position) {
		Matrix rotated = roR.times(position);
		if (shift != null)
			rotated.plus(shift);
		return rotated;
	}

	@Override
	public void setTransformMatrix(Matrix m) {
		roR = m;
	}

	@Override
	public void setShift(Matrix d) {
		shift = d;
//		for (int i=0; i<3; i++)
//			afT.set(i, 3, d.get(i, 0));
//		afT.set(3, 3, 1.);
	}

	@Override
	public void setRotation(Matrix n, double r) {
		angle = r;
		this.n = n;
	}

	@Override
	public boolean isAffine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setScalingOnSurface(double scaling) {
		this.scaling = scaling;
	}

	@Override
	public void setSurfaceNormal(Matrix n) {
		
	}

	@Override
	public Point2D transformToSurface(Matrix vec) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransformationToSurface compose(TransformationToSurface t) {
		return null;
	}

}
