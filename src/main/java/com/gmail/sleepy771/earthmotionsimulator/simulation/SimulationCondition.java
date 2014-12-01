package com.gmail.sleepy771.earthmotionsimulator.simulation;

public interface SimulationCondition {
	boolean satisfies();

	void forceFalse();
	
	void forceTrue();
	
	void reset();
}
