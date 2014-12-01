package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public class RegularTransformation implements Transformation {

	private Matrix transM;
	private int dim;
	
	public RegularTransformation(Matrix m) {
		dim = m.getRowDimension();
		setMatrix(m);
	}
	
	public RegularTransformation(int dim) {
		this.dim = dim;
		transM = Matrix.identity(dim, dim);
	}
	
	@Override
	public Matrix getMatrix() {
		return transM;
	}

	@Override
	public void setMatrix(Matrix m) {
		checkMatrix(m);
		transM = m;
	}

	@Override
	public Transformation leftTranslation(Transformation t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Matrix transform(Matrix v) {
		return transM.times(v);
	}

	@Override
	public int dimension() {
		return dim;
	}
	
	private void checkMatrix(Matrix m) {
		if (!(m.getColumnDimension() == dim && m.getRowDimension() == dim && m.det() != 0.))
			throw new IllegalArgumentException("This is not a valid matrix");
	}

}
