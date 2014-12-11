package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Date;

public class DrawableLegend implements DrawableString, DrawableShape, ColorizedDrawable, PositionableDrawable {
	
	enum Alignment {
		TOP, BOTTOM, LEFT, RIGHT, TOP_RIGHT, BOTTOM_RIGHT, TOP_LEFT, BOTTOM_LEFT, CENTER, FIXED;
	}
	
	private DrawableShape shape;
	private DrawableString text;
	
	private Alignment align;
	
	private Date date;
	private String simName;
	
	private double padding;
	
	@Override
	public void draw(Graphics2D g2d) {
		shape.draw(g2d);
		text.draw(g2d);
	}

	@Override
	public void setColor(Color c) {
		shape.setColor(c);
	}

	@Override
	public Color getColor() {
		return shape.getColor();
	}
	
	public Color getFontColor() {
		return text.getColor();
	}
	
	public void setFontColor(Color c) {
		text.setColor(c);
	}

	@Override
	public void setPosition(Point2D p) {
		if (align == Alignment.FIXED) {
			setInnerPosition(p);
		}
	}
	
	private void setInnerPosition(Point2D p) {
		shape.setPosition(p);
		text.setPosition(p);
	}

	@Override
	public Point2D getPosition() {
		return shape.getPosition();
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
	public void setDisplacement(double d) {
		text.setDisplacement(d);
	}

	@Override
	public double getDisplacement() {
		return text.getDisplacement();
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
	public void setAffineTransform(AffineTransform af) {
		shape.setAffineTransform(af);
	}

	@Override
	public AffineTransform getAffineTransform() {
		return shape.getAffineTransform();
	}
	
	public void setPadding(double pad) {
		this.padding = pad;
	}
	
	public double getPadding() {
		return this.padding;
	}
	
	public void setTime(double time) {
		setTime(new Date(Math.round(time)));
	}
	
	public void setTime(Date d) {
		this.date = d;
		updateText();
	}
	
	public void setSimulationName(String name) {
		this.simName = name;
		updateText();
	}
	
	private void updateText() {
		StringBuilder sb = new StringBuilder(this.simName);
		sb.append("\nDate: ").append(this.date.toString());
		setText(sb.toString());
	}
	
	public void setAlingment(Alignment a) {
		this.align = a;
	}
	
	public Alignment getAlignment() {
		return this.align;
	}

	@Override
	public Shape getShape() {
		return shape.getShape();
	}

}
