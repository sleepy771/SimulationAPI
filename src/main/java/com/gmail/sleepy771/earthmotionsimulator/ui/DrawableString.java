package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Font;

public interface DrawableString extends PointDrawable {
	void setText(String str);
	
	String getText();
	
	void setFont(Font f);
	
	Font getFont();
	
	void setDisplacement(double d);
	
	double getDisplacement();
}
