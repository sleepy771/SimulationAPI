package com.gmail.sleepy771.earthmotionsimulator.ui.transformation;

import Jama.Matrix;

public interface AffineTransformation extends Transformation {
	Matrix getShift();
	
	void setShift(Matrix d);
	
	void setTransformation(Transformation t);
	
	AffineTransformation leftTranslation(Transformation t);
	
	Transformation getTransformation();
}
