package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.awt.geom.Point2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class SlickShapeDrawable implements SlickPositionableDrawable {
	private Shape shape;
	private Color fillColor, strokeColor;
	private float thicknes;
	
	public SlickShapeDrawable() {
		this(null, null, null, 1f);
	}
	
	public SlickShapeDrawable(Shape s) {
		this(s, null, null, 1f);
	}
	
	public SlickShapeDrawable(Shape s, Color fC) {
		this(s, fC, null, 1f);
	}
	
	public SlickShapeDrawable(Shape s, Color fC, Color sC, float t) {
		setShape(s);
		setColor(fC);
		setStrokeColor(sC);
		setThicknes(t);
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
		if (c == null) {
			fillColor = Color.white;
			return;
		}
		fillColor = c;
	}
	
	public void setShape(Shape s) {
		if (shape == null) {
			shape = new Rectangle(0f, 0f, 20f, 20f);
			return;
		}
		shape = s;
	}
	
	public void setStrokeColor(Color c) {
		strokeColor = c;
	}
	
	public void setThicknes(float w) {
		if (w <= 0f) {
			thicknes = 1f;
			return;
		}
		thicknes = w;
	}
	
	public Shape getShape() {
		return shape;
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
