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
	private Point2D position;
	
	public DrawablePlanet(DrawableShape sh, DrawableString st) {
		shape = sh;
		text = st;
		shape.setAffineTransform(getAffineTransform());
		text.setAffineTransform(getAffineTransform());
	}
	
	@Override
	public void setPosition(Point2D p) {
		text.setPosition(position);
		shape.setPosition(position);
		position = p;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public void draw(Graphics2D g2d) {
		shape.draw(g2d);
		text.draw(g2d);
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		this.transform = af;
		text.setAffineTransform(transform);
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
	public void setStroke(boolean stroke) {
		shape.setStroke(stroke);
	}

	@Override
	public void setStrokeWidth(double width) {
		shape.setStrokeWidth(width);
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

}
