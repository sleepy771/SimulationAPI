package com.gmail.sleepy771.earthmotionsimulator.simulation;

import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public interface SimulationSystem<T extends SystemUnit> {
	List<T> getUnits();
	
	void setUnits(List<T> list);
	
	List<T> getMovedUnits();
	
	void fireUpdate();
	
	Simulation createSimualtion();

	boolean canCreateSimulation();
	
	void flush();
}
