package com.gmail.sleepy771.earthmotionsimulator.ui.transformation;

import Jama.Matrix;

public interface Transformation {
	Matrix getMatrix();
	
	void setMatrix(Matrix m);
	
	Transformation leftTranslation(Transformation t);
	
	Transformation rightTranslation(Transformation t);
	
	Matrix transform(Matrix v);
	
	int dimension();
}
