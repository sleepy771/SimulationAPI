package com.gmail.sleepy771.earthmotionsimulator.ui.transformation;

import Jama.Matrix;

public interface Rotation extends Transformation {
	Matrix getAxis();
	
	double getAngle();
	
	void setAxis(Matrix n);
	
	void setAngle(double angle);
}
