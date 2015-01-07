package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.awt.geom.Point2D;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class SlickDrawableText implements SlickPositionableDrawable {

	private Point2D textPosition;
	private String text;
	private Color textColor;
	
	public SlickDrawableText(String t, Point2D p, Color c) {
		textPosition = p;
		text = t;
		textColor = c;
	}
	
	@Override
	public void draw(Graphics g) {
		g.setColor(textColor);
		g.drawString(text, (float) textPosition.getX(), (float) textPosition.getY());
	}

	@Override
	public void setPosition(Point2D p) {
		textPosition = p;
	}

	@Override
	public Point2D getPosition() {
		return textPosition;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setColor(Color c) {
		textColor = c;
	}

}
