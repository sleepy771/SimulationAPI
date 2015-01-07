package com.gmail.sleepy771.earthmotionsimulator.ui.slick;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickDrawable;

public class SimulationRenderer extends BasicGame {

	private SlickDrawable drawable;
	private boolean antiAliasing = false;
	private Color backgound = Color.black;
	
	public SimulationRenderer(String name) {
		super(name);
	}
	
	public SimulationRenderer() {
		this("Simulation");
	}
	
	public void setDrawable(SlickDrawable d) {
		drawable = d;
	}
	
	public void setBackgroundColor(Color bg) {
		
	}
	
	public SlickDrawable getDrawable() {
		return drawable;
	}
	
	public void setAntiAliasing(boolean aa) {
		this.antiAliasing = aa;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		g.setAntiAlias(antiAliasing);
		g.setBackground(backgound);
		if (drawable != null)
			drawable.draw(g);
	}

	@Override
	public void init(GameContainer gc) throws SlickException {
		
	}

	@Override
	public void update(GameContainer gc, int arg1) throws SlickException {
		
	}

}
