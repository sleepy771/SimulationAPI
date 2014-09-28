package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public interface Drawable {
	void draw(Graphics2D g2d);
	
	void setPosition(Point2D p);
	
	Point2D getPosition();
}
