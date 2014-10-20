package com.gmail.sleepy771.earthmotionsimulator.ui;

import com.gmail.sleepy771.earthmotionsimulator.Body;

public interface DrawableBody extends DrawableShape {
	void setBody(Body b);
	
	Body getBody();
}
