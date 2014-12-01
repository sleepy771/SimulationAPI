package com.gmail.sleepy771.earthmotionsimulator.objects;

import Jama.Matrix;

public interface Body extends SystemUnit {
	Matrix getMomentum();
	
	Matrix getPosition();
	
	Matrix getSpeed();
	
	double getMass();
	
	Object getId();
}
