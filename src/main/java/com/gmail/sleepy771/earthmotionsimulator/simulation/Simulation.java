package com.gmail.sleepy771.earthmotionsimulator.simulation;

public interface Simulation {
	void nextStep();
	
	boolean canMakeNextStep();
}
