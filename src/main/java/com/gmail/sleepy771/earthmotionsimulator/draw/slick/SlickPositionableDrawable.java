package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.awt.geom.Point2D;

public interface SlickPositionableDrawable extends SlickDrawable {
	void setPosition(Point2D p);
	
	Point2D getPosition();
}
