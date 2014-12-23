package com.gmail.sleepy771.earthmotionsimulator.transform;

import java.awt.geom.Point2D;

import Jama.Matrix;

public interface AffineTransformToPlane extends AffineTransform {
	Point2D asPoint(Matrix v);
}
