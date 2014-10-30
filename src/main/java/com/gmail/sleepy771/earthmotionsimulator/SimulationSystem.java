package com.gmail.sleepy771.earthmotionsimulator;

import java.util.List;

public interface SimulationSystem<T extends SystemUnit> {
	List<T> getUnits();
	
	void setUnits(List<T> list);
	
	List<T> getMovedUnits();
	
	void fireUpdate();
	
	Simulation createSimualtion();

	boolean canCreateSimulation();
}
