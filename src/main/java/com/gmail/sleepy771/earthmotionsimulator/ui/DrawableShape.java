package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Shape;
import java.awt.Stroke;

public interface DrawableShape extends PointDrawable {
	void setShape(Shape s);
	
	void setFill(boolean fill);
	
	void setFillColor(Color fillColor);
	
	void setStroke(boolean stroke);
	
	void setStrokeWidth(double width);
	
	void setStrokePattern(Stroke stroke);
	
	void setStrokeColor(Color strokeColor);
	
	Color getFillColor();
}
