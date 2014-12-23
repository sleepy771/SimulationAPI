package com.gmail.sleepy771.earthmotionsimulator.simulation.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;

public class BaseThreadSynchronizer implements ThreadSynchronizer {

	private final Lock syncLock;
	private final Lock counterLock;
	private final Condition syncCondition;
	private final Set<Simulation> synchronizedSimulations;
	private int waitForThreads;
	
	public BaseThreadSynchronizer() {
		syncLock = new ReentrantLock();
		counterLock = new ReentrantLock();
		syncCondition = syncLock.newCondition();
		synchronizedSimulations = new HashSet<>();
		waitForThreads = 0;
	}
	
	@Override
	public void synchronize() throws InterruptedException {
		atemptToResetCounter();
		decrementCounter();
		syncLock.lock();
		try {	
			while (shouldWait()) {
				System.out.println("waiting");
				syncCondition.await();
			}
		} finally {
			syncLock.unlock();
		}
	}
	
	public void signalAll() {
		syncLock.lock();
		try {
			syncCondition.signalAll();
		} finally {
			syncLock.unlock();
		}
	}
	
	public boolean shouldWait() {
		counterLock.lock();
		try {
			return waitForThreads > 0;
		} finally {
			counterLock.unlock();
		}
	}
	
	public void decrementCounter() {
		counterLock.lock();
		waitForThreads--;
		counterLock.unlock();
	}
	
	public void atemptToResetCounter() {
		counterLock.lock();
		if (waitForThreads == 0)
			waitForThreads = synchronizedSimulations.size();
		counterLock.unlock();
	}

	@Override
	public void register(Simulation s) {
		syncLock.lock();
		synchronizedSimulations.add(s);
		syncCondition.signalAll();
		syncLock.unlock();
	}

	@Override
	public void unregister(Simulation s) {
		syncLock.lock();
		synchronizedSimulations.remove(s);
		syncLock.unlock();
	}

}
