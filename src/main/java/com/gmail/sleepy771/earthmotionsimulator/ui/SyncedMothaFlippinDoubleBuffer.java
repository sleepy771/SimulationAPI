package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.VolatileImage;
import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.gmail.sleepy771.earthmotionsimulator.DataServer;
import com.gmail.sleepy771.earthmotionsimulator.Receiver;
import com.gmail.sleepy771.earthmotionsimulator.SpaceSimulationRecord;

public class SyncedMothaFlippinDoubleBuffer {
	private VolatileImage[] backBuffer;
	private int page;
	private int loopDelay;
	private final JPanel canvas;
	private Drawable drawable;
	private final Lock bufferLock;
	private final Condition canRender;
	private Runnable renderToBackbuffer;
	private Thread backbufferThread;
	
	public SyncedMothaFlippinDoubleBuffer(JPanel canvas) {
		this.canvas = canvas;
		bufferLock = new ReentrantLock();
		canRender = bufferLock.newCondition();
		backBuffer = new VolatileImage[2];
		page = 0;
		renderToBackbuffer = new Runnable() {
			@Override
			public void run() {
				try {
					while(true) {
						bufferLock.lock();
						try {
							canRender.await();
							renderOffscreen();
						} finally {
							bufferLock.unlock();
						}
					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
			}
		};
		backbufferThread = new Thread(renderToBackbuffer);
		Timer timer = new Timer(loopDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				renderToCanvas();
			}
		});
		timer.start();
	}
	
	public void startBackbufferRendering() {
		this.backbufferThread.start();
	}
	
	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}
	
	public void renderOffscreen() {
		int drawIn = getDrawInImage();
		do {
			int validationCode = backBuffer[drawIn].validate(canvas.getGraphicsConfiguration());
			if (validationCode == VolatileImage.IMAGE_INCOMPATIBLE) {
				backBuffer[drawIn] = canvas.createVolatileImage(canvas.getWidth(), canvas.getHeight());
			}
			backBuffer[drawIn].flush();
			Graphics2D g2d = backBuffer[drawIn].createGraphics();
			try {
				drawable.draw(g2d);
			} finally {
				g2d.dispose();
			}
		} while (backBuffer[drawIn].contentsLost());
	}
	
	public void renderToCanvas() {
		Graphics g = canvas.getGraphics();
		try {
			VolatileImage renderedImage = backBuffer[getRenderedImage()];
			fireBackBufferRendering();
			int validation = renderedImage.validate(canvas.getGraphicsConfiguration());
			if (!renderedImage.contentsLost() && validation != VolatileImage.IMAGE_INCOMPATIBLE);
				g.drawImage(renderedImage, 0, 0, null);
		} finally {
			g.dispose();
		}
	}
	
	private int getDrawInImage() {
		bufferLock.lock();
		try {
			return 1 - page;
		} finally {
			bufferLock.unlock();
		}
	}
	
	private int getRenderedImage() {
		bufferLock.lock();
		try {
			return page;
		} finally {
			bufferLock.unlock();
		}
	}
	
	private void fireBackBufferRendering() {
		bufferLock.lock();
		try {
			page = (page + 1) % 2;
			canRender.signal();
		} finally {
			bufferLock.unlock();
		}
	}
}
