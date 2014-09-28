package com.gmail.sleepy771.earthmotionsimulator;

public interface Simulation {
	void makeStep(Body b);
	
	void setTimeDelta(double time);
}
