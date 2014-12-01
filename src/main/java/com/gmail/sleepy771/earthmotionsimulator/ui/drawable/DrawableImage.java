package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Image;
import java.awt.geom.Dimension2D;

public interface DrawableImage extends PositionableDrawable {
	void setImage(Image img);
	
	Image getImage();
	
	void setDimension(Dimension2D imageDim);
	
	Dimension2D getDimension();
	
	Image render();
}
