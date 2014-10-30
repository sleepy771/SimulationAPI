	package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.geom.Point2D;

public interface PointDrawable extends Drawable {
	void setPosition(Point2D p);
	
	Point2D getPosition();
}
