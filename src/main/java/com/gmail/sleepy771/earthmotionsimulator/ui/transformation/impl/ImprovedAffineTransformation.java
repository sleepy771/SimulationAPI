package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;
/**
 * Kodim jak boh!!!
 * @author sleepy771
 */
import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.AffineTransformation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public class ImprovedAffineTransformation implements AffineTransformation {

	private boolean isAffineInnerT;
	private Transformation innerT;
	private Matrix shift;
	private int dim;
	
	public ImprovedAffineTransformation(int dim) {
		this.dim = dim;
		innerT = new RegularTransformation(dim);
		shift = new Matrix(dim, 1);
	}
	
	public ImprovedAffineTransformation(Matrix m) {
		this.dim = m.getColumnDimension();
		setMatrix(m);
		shift = new Matrix(dim, 1);
	}
	
	public ImprovedAffineTransformation(Matrix m, Matrix s) {
		dim = m.getRowDimension();
		setMatrix(m);
		setShift(s);
	}
	
	public ImprovedAffineTransformation(Transformation t) {
		dim = t.dimension();
		setTransformation(t);
		isAffineInnerT = false;
		if (AffineTransformation.class.isInstance(t)) {
			isAffineInnerT = true;
		}
	}
	
	public ImprovedAffineTransformation(Transformation t, Matrix s) {
		dim = t.dimension();
		isAffineInnerT = false;
		setShift(s);
		setTransformation(t);
	}
	
	public ImprovedAffineTransformation(AffineTransformation t) {
		dim = t.dimension();
		setTransformation(t);
		isAffineInnerT = true;
	}
	
	@Override
	public Matrix getMatrix() {
		return innerT.getMatrix();
	}

	@Override
	public void setMatrix(Matrix m) {
		checkMatrix(m);
		if (innerT == null) {
			createBaseRegularTransformation(m);
			return;
		}
		innerT.setMatrix(m);
	}

	private void createBaseRegularTransformation(Matrix m) {
		innerT = new RegularTransformation(m);
	}

	@Override
	public AffineTransformation leftTranslation(Transformation t) {
		return new ImprovedAffineTransformation(t.getMatrix().times(getMatrix()), t.transform(shift));
	}
	
	private Matrix transWithoutShift(Matrix m) {
		return getMatrix().times(m);
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		if (AffineTransformation.class.isInstance(t)) {
			AffineTransformation rightAf= AffineTransformation.class.cast(t);
			return new ImprovedAffineTransformation(transWithoutShift(rightAf.getMatrix()), transform(rightAf.getShift()));
		}
		return new ImprovedAffineTransformation(transWithoutShift(t.getMatrix()), shift.copy());
	}

	@Override
	public Matrix transform(Matrix v) {
		Matrix u = innerT.transform(v);
		if (shift != null)
			u.plusEquals(shift);
		return u;
	}

	@Override
	public int dimension() {
		return dim;
	}

	@Override
	public Matrix getShift() {
		if (isAffineInnerT)
			return AffineTransformation.class.cast(innerT).getShift();
		return shift;
	}

	@Override
	public void setShift(Matrix d) {
		checkShiftVector(d);
		if (isAffineInnerT) {
			AffineTransformation.class.cast(innerT).setShift(d);
			return;
		}
		shift = d;
	}
	
	private void checkShiftVector(Matrix v) {
		if(!(v.getColumnDimension()==1 && v.getRowDimension() == dim))
			throw new IllegalArgumentException("Wrong vector dimension");
	}
	
	private void checkMatrix(Matrix m) {
		if (!(m.getRowDimension() == dim && m.getColumnDimension() == dim))
			throw new IllegalArgumentException("Invalid dimension");
	}
	
	private void checkTransformation(Transformation t) {
		if (dim != t.dimension()) {
			throw new IllegalArgumentException("Invalid transformation");
		}
	}

	@Override
	public void setTransformation(Transformation t) {
		checkTransformation(t);
		innerT = t;
		if (AffineTransformation.class.isInstance(t)) {
			isAffineInnerT = true;
			shift = null;
		}
	}

	@Override
	public Transformation getTransformation() {
		return innerT;
	}

}
