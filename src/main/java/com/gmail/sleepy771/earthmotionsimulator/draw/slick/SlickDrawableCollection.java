package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.newdawn.slick.Graphics;

public class SlickDrawableCollection implements SlickPositionableDrawable {

	private List<SlickPositionableDrawable> drawableList;
	private Lock listLock;
	private Lock positionLock;
	private Point2D position;
	
	public SlickDrawableCollection() {
		listLock = new ReentrantLock();
		positionLock = new ReentrantLock();
		drawableList = new ArrayList<>();
	}
	
	@Override
	public final void draw(Graphics g) {
		listLock.lock();
		for (SlickDrawable d : drawableList)
			d.draw(g);
		listLock.unlock();
	}

	@Override
	public void setPosition(Point2D p) {
		listLock.lock();
		positionLock.lock();
		position = p;
		positionLock.unlock();
		for (SlickPositionableDrawable d : drawableList) {
			d.setPosition(p);
		}
		listLock.unlock();
	}
	
	public void addDrawable(SlickPositionableDrawable d) {
		listLock.lock();
		drawableList.add(d);
		listLock.unlock();
	}
	
	public void removeDrawable(SlickPositionableDrawable d) {
		listLock.lock();
		drawableList.remove(d);
		listLock.unlock();
	}

	@Override
	public Point2D getPosition() {
		positionLock.lock();
		try {
			return position;
		} finally {
			positionLock.unlock();
		}
	}

}
