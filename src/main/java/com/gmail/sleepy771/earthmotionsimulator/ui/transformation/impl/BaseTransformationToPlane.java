package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import java.awt.geom.Point2D;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.AffineTransformation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.TransformationToPlane;
// 'raped
public class BaseTransformationToPlane implements TransformationToPlane {

	private AffineTransformation innerT;
	
	public BaseTransformationToPlane(AffineTransformation af) {
		innerT = af;
	}
	
	@Override
	public Matrix getMatrix() {
		return innerT.getMatrix();
	}

	@Override
	public void setMatrix(Matrix m) {
		innerT.setMatrix(m);
	}

	@Override
	public AffineTransformation leftTranslation(Transformation t) {
		return innerT.leftTranslation(t);
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		return innerT.rightTranslation(t);
	}

	@Override
	public Matrix transform(Matrix v) {
		return innerT.transform(v);
	}

	@Override
	public int dimension() {
		return innerT.dimension();
	}

	@Override
	public Point2D asPoint(Matrix v) {
		Matrix p = transform(v);
		return new Point2D.Double(p.get(0, 0), p.get(1, 0));
	}

	@Override
	public Matrix getShift() {
		return innerT.getShift();
	}

	@Override
	public void setShift(Matrix d) {
		innerT.setShift(d);
	}

	@Override
	public void setTransformation(Transformation t) {
		if (AffineTransformation.class.isInstance(t))
			innerT = AffineTransformation.class.cast(t);
		else
			innerT.setTransformation(t);
	}

	@Override
	public Transformation getTransformation() {
		return innerT.getTransformation();
	}

}
