package com.gmail.sleepy771.earthmotionsimulator.simulation;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractSimulationCondition implements
		SimulationCondition {

	private byte forcedValue;
	private final Lock conditionLock;
	
	public AbstractSimulationCondition() {
		forcedValue = -1;
		conditionLock = new ReentrantLock();
	}
	
	@Override
	public boolean satisfies() {
		conditionLock.lock();
		try {
			switch (forcedValue) {
				case 0: return false;
				case 1: return true;
				default: return condition();
			}
		} finally {
			conditionLock.unlock();
		}
	}
	
	public abstract boolean condition();

	@Override
	public void forceFalse() {
		conditionLock.lock();
		forcedValue = 0;
		conditionLock.unlock();
	}

	@Override
	public void forceTrue() {
		conditionLock.lock();
		forcedValue = 1;
		conditionLock.unlock();
	}
	
	@Override
	public void reset() {
		conditionLock.lock();
		forcedValue = -1;
		conditionLock.unlock();
	}

}
