package com.gmail.sleepy771.earthmotionsimulator.ui.transformation;

import java.awt.geom.Point2D;

import Jama.Matrix;

public interface TransformationToPlane extends AffineTransformation {
	Point2D asPoint(Matrix v);
}
