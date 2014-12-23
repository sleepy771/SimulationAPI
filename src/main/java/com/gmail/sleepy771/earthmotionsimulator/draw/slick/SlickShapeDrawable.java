package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.awt.geom.Point2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Shape;

public class SlickShapeDrawable implements SlickPositionableDrawable {
	private Shape shape;
	private Color fillColor, strokeColor;
	private float thicknes;
	
	public SlickShapeDrawable(Shape s) {
		shape = s;
	}
	
	@Override
	public Point2D getPosition() {
		return new Point2D.Float(shape.getCenterX(), shape.getCenterY());
	}
	
	@Override
	public void setPosition(Point2D p) {
		shape.setCenterX((float) p.getX());
		shape.setCenterY((float) p.getY());
	}
	
	public void setColor(Color c) {
		fillColor = c;
	}
	
	public void setShape(Shape s) {
		shape = s;
	}
	
	public void setStrokeColor(Color c) {
		strokeColor = c;
	}
	
	public void setStrokeThicknes(float w) {
		thicknes = w;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(fillColor);
		g.fill(shape);
		if (strokeColor != null)
			g.setColor(strokeColor);
		g.setLineWidth(thicknes);
		g.draw(shape);
	}

}
