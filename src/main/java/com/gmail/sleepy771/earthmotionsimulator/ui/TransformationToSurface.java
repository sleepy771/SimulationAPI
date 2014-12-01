package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.geom.Point2D;

import Jama.Matrix;

public interface TransformationToSurface extends LinearTransformation {
	void setScalingOnSurface(double scaling);
	
	void setSurfaceNormal(Matrix n);
	
	Point2D transformToSurface(Matrix vec);
	
	TransformationToSurface compose(TransformationToSurface t);
}
