package com.gmail.sleepy771.earthmotionsimulator;

import Jama.Matrix;

public interface Body extends SystemUnit {
	Matrix getMomentum();
	
	Matrix getPosition();
	
	Matrix getSpeed();
	
	double getMass();
	
	int signature();
}
