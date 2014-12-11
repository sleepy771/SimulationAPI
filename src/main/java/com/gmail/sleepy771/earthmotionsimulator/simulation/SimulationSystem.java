package com.gmail.sleepy771.earthmotionsimulator.simulation;

import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.Record;
import com.gmail.sleepy771.earthmotionsimulator.Recordable;
import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public interface SimulationSystem<T extends SystemUnit, R extends Record> extends Recordable<R> {
	List<T> getUnits();
	
	void setUnits(List<T> list);
	
	List<T> getCache();
	
	void addToCache(T u);
	
	void performUpdate();
	
	Simulation createSimualtion();

	boolean canCreateSimulation();
	
	void flush();
}
