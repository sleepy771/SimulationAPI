package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class DrawablePlanet implements DrawableString, DrawableShape {

	private final DrawableShape shape;
	private final DrawableString text;
	private AffineTransform transform;
	
	public DrawablePlanet(DrawableShape sh, DrawableString st, Point2D p) {
		shape = sh;
		text = st;
		Dimension2D rect = sh.getSize();
		text.setDisplacement(Math.hypot(rect.getHeight(), rect.getWidth()) + 5);
		shape.setAffineTransform(getAffineTransform());
		setPosition(p);
	}
	
	@Override
	public void setPosition(Point2D p) {
		text.setPosition(p);
		shape.setPosition(p);
	}

	@Override
	public Point2D getPosition() {
		return shape.getPosition();
	}

	@Override
	public void draw(Graphics2D g2d) {
		shape.draw(g2d);
		text.draw(g2d);
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		this.transform = af;
		shape.setAffineTransform(transform);
	}

	@Override
	public AffineTransform getAffineTransform() {
		if (transform == null) {
			transform = new AffineTransform();
		}
		return transform;
	}

	@Override
	public void setColor(Color c) {
		text.setColor(c);
	}

	@Override
	public Color getColor() {
		return text.getColor();
	}

	@Override
	public void setShape(Shape s) {
		shape.setShape(s);
	}

	@Override
	public void setFill(boolean fill) {
		shape.setFill(fill);
	}

	@Override
	public void setFillColor(Color fillColor) {
		shape.setFillColor(fillColor);
	}

	@Override
	public void setDrawStroke(boolean stroke) {
		shape.setDrawStroke(stroke);
	}

	@Override
	public void setStrokePattern(Stroke stroke) {
		shape.setStrokePattern(stroke);
	}

	@Override
	public void setStrokeColor(Color strokeColor) {
		shape.setStrokeColor(strokeColor);
	}

	@Override
	public Color getFillColor() {
		return shape.getFillColor();
	}

	@Override
	public void setText(String str) {
		text.setText(str);
	}

	@Override
	public String getText() {
		return text.getText();
	}

	@Override
	public void setFont(Font f) {
		text.setFont(f);
	}

	@Override
	public Font getFont() {
		return text.getFont();
	}

	@Override
	public Dimension2D getSize() {
		return shape.getSize();
	}

	@Override
	public void setDisplacement(double d) {
		text.setDisplacement(d);
	}

	@Override
	public double getDisplacement() {
		return text.getDisplacement();
	}

}
