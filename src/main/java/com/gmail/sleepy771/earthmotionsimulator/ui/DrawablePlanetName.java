package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class DrawablePlanetName implements DrawableString {

	private Point2D position;
	private String planetName;
	
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
		g2d.drawString(planetName, (float) position.getX(), (float) position.getY());
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
	public void setText(String str) {
		this.planetName = str;
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
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension2D getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
