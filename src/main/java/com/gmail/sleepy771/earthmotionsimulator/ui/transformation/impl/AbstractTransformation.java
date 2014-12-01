package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public abstract class AbstractTransformation implements Transformation {

	private Transformation rightTrans;
	private Matrix matrix;
	private Matrix composed;
	
	@Override
	public Matrix getMatrix() {
		if (composed == null) {
			if (rightTrans !=null)
				composed = matrix.times(rightTrans.getMatrix());
			else
				composed = matrix;
		}
		return composed;
	}

	@Override
	public void setMatrix(Matrix m) {
		this.matrix = m;
		this.composed = null;
	}

	@Override
	public Transformation leftTranslation(Transformation t) {
		return null;
	}

	@Override
	public Transformation rightTranslation(Transformation t) {
		return t.leftTranslation(this);
	}

	@Override
	public Matrix transform(Matrix v) {
		// TODO Auto-generated method stub
		return null;
	}

}
