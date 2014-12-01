package com.gmail.sleepy771.earthmotionsimulator.simulation;

public abstract class AbstarctSimulationCondition implements
		SimulationCondition {

	private byte forcedValue;
	
	public AbstarctSimulationCondition() {
		forcedValue = -1;
	}
	
	@Override
	public boolean satisfies() {
		switch (forcedValue) {
		case 0: return false;
		case 1: return true;
		default: return condition();
		}
	}
	
	public abstract boolean condition();

	@Override
	public void forceFalse() {
		forcedValue = 0;
	}

	@Override
	public void forceTrue() {
		forcedValue = 1;
	}
	
	@Override
	public void reset() {
		forcedValue = -1;
	}

}
