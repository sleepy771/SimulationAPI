package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;

public interface Drawable {
	void draw(Graphics2D g2d);
	
	void setColor(Color c);
	
	Color getColor();
	
	Dimension2D getSize(); // This one is questionable
}
