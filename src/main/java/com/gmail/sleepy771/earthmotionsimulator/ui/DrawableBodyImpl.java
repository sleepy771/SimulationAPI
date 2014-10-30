package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.gmail.sleepy771.earthmotionsimulator.Body;

public class DrawableBodyImpl implements DrawableBody, DrawableString {
	
	private Shape bodyShape;
	private boolean fill;
	private Color fillColor;
	private Color strokeColor;
	private double strokeWidth;
	private Stroke stroke;
	private Point2D position;
	private Object body;
	private AffineTransform affineTransformation;
	private Font font;
	private String text;
	
	@Override
	public void setShape(Shape s) {
		this.bodyShape = s;
	}

	@Override
	public void setFill(boolean fill) {
		this.fill = fill;
	}

	@Override
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	@Override
	public void setStroke(boolean stroke) {
		
	}

	@Override
	public void setStrokeWidth(double width) {
		this.strokeWidth = width;
	}

	@Override
	public void setStrokePattern(Stroke stroke) {
		this.stroke = stroke;
	}

	@Override
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}

	@Override
	public Color getFillColor() {
		return this.fillColor;
	}

	@Override
	public void setPosition(Point2D p) {
		this.position = p;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.setStroke(stroke);
		g2d.setPaint(strokeColor);
		AffineTransform innerAf = new AffineTransform(affineTransformation);
		innerAf.translate(position.getX(), position.getY());
		Shape transformed = innerAf.createTransformedShape(bodyShape);
		g2d.draw(transformed);
		g2d.setColor(fillColor);
		g2d.fill(transformed);
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		this.affineTransformation = af;
	}

	@Override
	public AffineTransform getAffineTransform() {
		return this.affineTransformation;
	}

	@Override
	public void setColor(Color c) {
		this.fillColor = c;
		this.strokeColor = c;
	}

	@Override
	public Color getColor() {
		return this.fillColor;
	}

	@Override
	public Dimension2D getSize() {
		return new Dimension2D() {
			private final Rectangle2D rect = DrawableBodyImpl.this.bodyShape.getBounds2D();
			
			@Override
			public double getWidth() {
				return rect.getWidth();
			}

			@Override
			public double getHeight() {
				return rect.getHeight();
			}

			@Override
			public void setSize(double width, double height) {
				rect.setRect(rect.getX(), rect.getY(), width, height);
			}
			
		};
	}

	@Override
	public void setBody(Body b) {
		this.body = b;
	}

	@Override
	public Body getBody() {
		return null;
	}

	@Override
	public void setText(String str) {
		this.text = str;
	}

	@Override
	public String getText() {
		return this.text;
	}

	@Override
	public void setFont(Font f) {
		this.font = f;
	}

	@Override
	public Font getFont() {
		return this.font;
	}

}
