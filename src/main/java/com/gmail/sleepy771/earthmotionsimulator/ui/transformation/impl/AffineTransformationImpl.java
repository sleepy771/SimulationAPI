package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.AffineTransformation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public class AffineTransformationImpl implements AffineTransformation {

	private Matrix matrix;
	private Matrix shift;
	private int dim;
	
	public AffineTransformationImpl(int dim) {
		this.dim = dim;
		matrix = null;
		shift = null;
	}
	
	public AffineTransformationImpl(Matrix m) {
		this.dim = m.getColumnDimension();
		setMatrix(m);
	}
	
	public AffineTransformationImpl(Matrix m, Matrix s) {
		dim = m.getRowDimension();
		setMatrix(m);
		setShift(s);
	}
	
	public AffineTransformationImpl(Transformation t) {
		dim = t.dimension();
		setMatrix(t.getMatrix());
		if (AffineTransformation.class.isInstance(t)) {
			AffineTransformation af = AffineTransformation.class.cast(t);
			setShift(af.getShift());
		}
	}
	
	public AffineTransformationImpl(AffineTransformation t) {
		dim = t.dimension();
		setMatrix(t.getMatrix());
		setShift(t.getShift());
	}
	
	@Override
	public Matrix getMatrix() {
		if (matrix == null) {
			matrix = Matrix.identity(dim, dim);
		}
		return matrix;
	}

	@Override
	public void setMatrix(Matrix m) {
		checkMatrix(m);
		matrix = m;
	}

	@Override
	public AffineTransformation leftTranslation(Transformation t) {
		if (t == null)
			return this;
		return leftProduct(new AffineTransformationImpl(t));
	}
	
	private AffineTransformation leftProduct(AffineTransformation af) {
		return new AffineTransformationImpl(getMatrix().times(af.getMatrix()), getMatrix().times(af.getShift()).plusEquals(getShift()));
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		return t.leftTranslation(this);
	}

	@Override
	public Matrix transform(Matrix v) {
		checkShiftVector(v);
		return getMatrix().times(v).plusEquals(getShift());
	}

	@Override
	public Matrix getShift() {
		if (shift == null) {
			shift = new Matrix(dim,1);
		}
		return shift;
	}

	@Override
	public void setShift(Matrix d) {
		checkShiftVector(d);
		this.shift = d;
	}
	
	private void checkShiftVector(Matrix v) {
		if(!(v.getColumnDimension()==1 && v.getRowDimension() == dim))
			throw new IllegalArgumentException("Wrong vector dimension");
	}
	
	private void checkMatrix(Matrix m) {
		if (!(m.getRowDimension() == dim && m.getColumnDimension() == dim))
			throw new IllegalArgumentException("Invalid dimension");
	}

	@Override
	public int dimension() {
		return dim;
	}

	@Override
	public void setTransformation(Transformation t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Transformation getTransformation() {
		// TODO Auto-generated method stub
		return null;
	}

}
