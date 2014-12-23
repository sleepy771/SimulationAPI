package com.gmail.sleepy771.earthmotionsimulator.transform;

import Jama.Matrix;

public interface AffineTransform {
	Matrix transform(Matrix v);
	
	void setShift(Matrix s);
	
	void setTransformation(Matrix m);
	
	void setMatrix(Matrix af);
	
	int getDomainDim();
	
	int getCodomainDim();
	
	Matrix getTransformation();
	
	Matrix getShift();
	
	Matrix getAugumentedMatrix();
	
	AffineTransform leftTranslation(AffineTransform af);
	
	boolean isInvertible();
	
	AffineTransform inverse();
}
