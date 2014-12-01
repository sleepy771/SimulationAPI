package com.gmail.sleepy771.earthmotionsimulator.simulation;

public interface Simulation {
	void makeStep();
	
	void setTimeDelta(double time);
	
	double getTimeDelta();
}
