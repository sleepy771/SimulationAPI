package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public interface Drawable {
	void draw(Graphics2D g2d);
	
	void setPosition(Point2D p);
	
	void setAffineTransform(AffineTransform af);
	
	AffineTransform getAffineTransform();
	
	Point2D getPosition();
}
