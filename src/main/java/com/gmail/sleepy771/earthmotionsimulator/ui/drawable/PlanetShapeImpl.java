package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class PlanetShapeImpl implements DrawableShape {

	private Point2D position;
	private AffineTransform affineTransform;
	private Shape shape;
	private Color fillColor;
	private Color strokeColor;
	private Stroke stroke;
	private boolean fillShape, drawStroke;
	
	
	public PlanetShapeImpl(Shape s, Point2D p) {
		this.position = p;
		this.shape = s;
	}
	
	
	@Override
	public void setPosition(Point2D p) {
		Rectangle2D rect = shape.getBounds2D();
		shape = AffineTransform.getTranslateInstance(p.getX() - rect.getCenterX(), p.getY() - rect.getCenterY()).createTransformedShape(shape);
		position = p;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public void draw(Graphics2D g2d) {
		drawStroke(g2d);
		drawFill(g2d);
	}

	private void drawFill(Graphics2D g2d) {
		if (!fillShape)
			return;
		if (fillColor != null)
			g2d.setColor(fillColor);
		g2d.fill(shape);
	}

	private void drawStroke(Graphics2D g2d) {
		if (!drawStroke)
			return;
		if (strokeColor != null)
			g2d.setColor(strokeColor);
		if (stroke != null)
			g2d.setStroke(stroke);
		g2d.draw(shape);
	}

	@Override
	public void setColor(Color c) {
		fillColor = c;
		strokeColor = c;
	}

	@Override
	public Color getColor() {
		return fillColor;
	}

	@Override
	public void setShape(Shape s) {
		this.shape = s;
	}

	@Override
	public void setFill(boolean fill) {
		fillShape = fill;
	}

	@Override
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void setDrawStroke(boolean draw) {
		drawStroke = draw;
	}

	@Override
	public void setStrokePattern(Stroke s) {
		stroke = s;
	}

	@Override
	public void setStrokeColor(Color color) {
		strokeColor = color;
	}

	@Override
	public Color getFillColor() {
		return fillColor;
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		affineTransform = af;
		shape = af.createTransformedShape(shape);
	}

	@Override
	public AffineTransform getAffineTransform() {
		return affineTransform;
	}


	@Override
	public Shape getShape() {
		return shape;
	}

}
