package com.gmail.sleepy771.earthmotionsimulator.ui;

import Jama.Matrix;

public interface LinearTransformation {
	Matrix transform(Matrix position);
	
	void setTransformMatrix(Matrix m);
	
	void setShift(Matrix d);
	
	void setRotation(Matrix n, double r);
	
	boolean isAffine();
}
