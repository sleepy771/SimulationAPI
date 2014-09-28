package com.gmail.sleepy771.earthmotionsimulator;

import java.util.List;

public interface System {
	void addBody(Body b);
	
	void removeBody(Body b);
	
	void contains(Body b);
	
	void setSimulation(Simulation s);
	
	Simulation getSimulation();
	
	List<Body> listAll();
}
