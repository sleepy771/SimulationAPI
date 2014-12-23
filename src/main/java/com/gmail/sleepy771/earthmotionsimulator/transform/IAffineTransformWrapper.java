package com.gmail.sleepy771.earthmotionsimulator.transform;

public interface IAffineTransformWrapper extends AffineTransform {
	AffineTransform getAffineTransform();
	
	void setAffineTransform(AffineTransform af);
	
	boolean hasInnerTransform();
}
