package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Graphics2D;
import java.util.List;

public class ListDrawable implements DrawableCollection {

	private List<Drawable> drawables;
	
	@Override
	public void draw(Graphics2D g2d) {
		for (Drawable d : drawables)
			d.draw(g2d);
	}

	@Override
	public void add(Drawable d) {
		if (!isPresent(d))
			drawables.add(d);
	}

	@Override
	public void remove(Drawable d) {
		drawables.remove(d);
	}
	
	private boolean isPresent(Drawable d) {
		for (Drawable drawble : drawables) {
			if (d.equals(drawble)) 
				return true;
		}
		return false;
	}

	@Override
	public void replace(Drawable oldDrawable, Drawable newDrawable) {
		for (int k=0; k<drawables.size(); k++) {
			if (drawables.get(k).equals(oldDrawable)) {
				drawables.set(k, newDrawable);
				break;
			}
		}
	}

	@Override
	public Drawable get(Object id) {
		int idx = Number.class.cast(id).intValue();
		return drawables.get(idx);
	}

}
