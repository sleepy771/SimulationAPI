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
	
	public DrawablePlanet(DrawableShape sh, DrawableString st) {
		shape = sh;
		text = st;
	}
	
	@Override
	public void setPosition(Point2D p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Point2D getPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AffineTransform getAffineTransform() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setShape(Shape s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFill(boolean fill) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFillColor(Color fillColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStroke(boolean stroke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStrokeWidth(double width) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStrokePattern(Stroke stroke) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStrokeColor(Color strokeColor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getFillColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFont(Font f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Font getFont() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension2D getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
