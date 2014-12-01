package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.VolatileImage;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import javax.swing.Timer;

import com.gmail.sleepy771.earthmotionsimulator.ui.drawable.Drawable;

public class Canvas extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9207418560165669010L;
	
	private VolatileImage backbuffer;
	private Drawable drawable;
	private final Timer animationTimer;
	
	public Canvas() {
		animationTimer = new Timer(50, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				repaint();
			}
		});
	}
	
	private void renderOffscreen() {
		do {
			if (backbuffer == null) 
				backbuffer = createVolatileImage(getWidth(), getHeight());
			int validationCode = backbuffer.validate(getGraphicsConfiguration());
			if (validationCode == VolatileImage.IMAGE_INCOMPATIBLE) {
				backbuffer = createVolatileImage(getWidth(), getHeight());
			}
			backbuffer.flush();
			Graphics2D g2d = backbuffer.createGraphics();
			try {
				if (drawable != null)
					drawable.draw(g2d);
			} finally {
				g2d.dispose();
			}
		} while(backbuffer.contentsLost());
	}
	
	public void setDrawable(Drawable d) {
		this.drawable = d;
	}
	
	public Drawable getDrawable() {
		return this.drawable;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		do {
			renderOffscreen();
			g.drawImage(backbuffer, 0, 0, null);
		}while (backbuffer.contentsLost());
	}
	
	public void startAnimation() {
		animationTimer.start();
	}
	
	public void stopAnimation() {
		animationTimer.stop();
	}
	
	public boolean isAnimationRunning() {
		return animationTimer.isRunning();
	}
	
	public void setDelay(int delay) {
		animationTimer.setDelay(delay);
	}
	
	public int getDelay() {
		return animationTimer.getDelay();
	}
}
