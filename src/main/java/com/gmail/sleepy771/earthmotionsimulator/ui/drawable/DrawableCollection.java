package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

public interface DrawableCollection extends Drawable {
	void add(Drawable d);
	
	Drawable get(Object id);
	
	void remove(Drawable d);
	
	void replace(Drawable oldDrawable, Drawable newDrawable);
}
