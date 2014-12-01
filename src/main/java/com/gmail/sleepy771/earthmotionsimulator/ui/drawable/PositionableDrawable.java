	package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.geom.Point2D;

public interface PositionableDrawable extends Drawable {
	void setPosition(Point2D p);
	
	Point2D getPosition();
}
