package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;

public class DrawableStringImpl implements DrawableString {

	private String text;
	private Point2D position;
	private Font font;
	private Color fontColor;
	private double displacement;
	
	public DrawableStringImpl(String t, Point2D p, double d) {
		text = t;
		position = p;
		this.displacement = d;
	}
	
	@Override
	public void setPosition(Point2D p) {
		position = p;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (font != null)
			g2d.setFont(font);
		if (fontColor != null)
			g2d.setColor(fontColor);
		g2d.drawString(text, (float) calculateX(g2d), (float) calculateY(g2d));
	}
	
	private double calculateX(Graphics2D g2d) {
		int w = g2d.getFontMetrics().stringWidth(text);
		if (position.getX() + displacement > g2d.getClipBounds().getMaxX()) {
			return position.getX() - displacement - w;
		}
		return position.getX() + displacement;
	}
	
	private double calculateY(Graphics2D g2d) {
		int h = g2d.getFontMetrics().getHeight();
		if (position.getY() - displacement - h < g2d.getClipBounds().getMinY()) {
			return position.getY() + displacement;
		}
		return position.getY() - displacement - h;
	}
	
	@Override
	public void setColor(Color c) {
		fontColor = c;
	}

	@Override
	public Color getColor() {
		return fontColor;
	}

	@Override
	public void setText(String str) {
		text = str;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public void setFont(Font f) {
		this.font = f;
	}

	@Override
	public Font getFont() {
		return font;
	}

	@Override
	public void setDisplacement(double d) {
		displacement = d;
	}

	@Override
	public double getDisplacement() {
		return displacement;
	}

}
