package com.gmail.sleepy771.earthmotionsimulator.simulation.impl;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;

public interface ThreadSynchronizer {
	void synchronize() throws InterruptedException;
	
	void signalAll();
	
	void register(Simulation s);
	
	void unregister(Simulation s);
}
