package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class SlickTrackingLine implements SlickPositionableDrawable {

	private List<Point2D> path;
	private float thicknes;
	private Color lineColor;
	
	public SlickTrackingLine() {
		path = new LinkedList<>();
	}
	
	public final void addPosition(Point2D p) {
		path.add(p);
	}
	
	public final void setLineThicknes(float t) {
		thicknes = t;
	}
	
	public final void setColor(Color c) {
		lineColor = c;
	}

	@Override
	public final void draw(Graphics g) {
		g.setColor(lineColor);
		g.setLineWidth(thicknes);
		Point2D prevPoint = null;
		for (Point2D p : path) {
			if (prevPoint != null)
				g.drawLine((float) prevPoint.getX(), (float) prevPoint.getY(), (float) p.getX(), (float) p.getY());
			prevPoint = p;
		}
	}

	@Override
	public void setPosition(Point2D p) {
		addPosition(p);
	}

	@Override
	public Point2D getPosition() {
		return path.get(path.size() - 1);
	}

}
