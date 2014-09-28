package com.gmail.sleepy771.earthmotionsimulator;

import Jama.Matrix;

public interface Body {
	Matrix getMomentum();
	
	Matrix getPosition();
	
	Matrix getSpeed();
	
	double getMass();
	
	void setMomentum(Matrix p);
	
	void setPosition(Matrix r);
}
