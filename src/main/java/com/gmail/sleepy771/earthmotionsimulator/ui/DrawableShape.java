package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

public interface DrawableShape extends PointDrawable {
	void setShape(Shape s);
	
	void setFill(boolean fill);
	
	void setFillColor(Color fillColor);
	
	void setDrawStroke(boolean stroke);
	
	void setStrokePattern(Stroke stroke);
	
	void setStrokeColor(Color strokeColor);
	
	Color getFillColor();
	
	void setAffineTransform(AffineTransform af);
	
	AffineTransform getAffineTransform();
}
