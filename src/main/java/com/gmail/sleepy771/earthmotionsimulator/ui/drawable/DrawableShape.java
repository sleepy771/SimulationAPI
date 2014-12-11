package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

public interface DrawableShape extends PositionableDrawable, ColorizedDrawable {
	void setShape(Shape s);
	
	void setFill(boolean fill);
	
	void setFillColor(Color fillColor);
	
	void setDrawStroke(boolean stroke);
	
	void setStrokePattern(Stroke stroke);
	
	void setStrokeColor(Color strokeColor);
	
	Color getFillColor();
	
	Shape getShape();
	
	void setAffineTransform(AffineTransform af);
	
	AffineTransform getAffineTransform();
}
