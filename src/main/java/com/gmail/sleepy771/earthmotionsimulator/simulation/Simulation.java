package com.gmail.sleepy771.earthmotionsimulator.simulation;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public interface Simulation {
	SystemUnit makeStep();
	
	void setTimeDelta(double time);
	
	double getTimeDelta();
}
