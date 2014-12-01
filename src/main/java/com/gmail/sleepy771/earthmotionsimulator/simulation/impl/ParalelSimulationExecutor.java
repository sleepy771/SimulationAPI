package com.gmail.sleepy771.earthmotionsimulator.simulation.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public class ParalelSimulationExecutor implements SimulationExecutor {

	private SimulationSystem<?> system;
	private ExecutorService threadPool;
	private SimulationCondition endCondition;
	private final Lock updateLock;
	private final Lock runningCounterLock;
	private final Lock endConditionLock;
	private final Condition canRun;
	private int planetsMoved;
	private int running;
	
	public ParalelSimulationExecutor() {
		this(null);
	}

	public ParalelSimulationExecutor(SimulationSystem<?> sys) {
		threadPool = Executors.newCachedThreadPool();
		this.system = sys;
		running = 0;
		this.updateLock = new ReentrantLock();
		canRun = updateLock.newCondition();
		runningCounterLock = new ReentrantLock();
		endConditionLock = new ReentrantLock();
	}

	@Override
	public void runSimulation() {
		if (this.system == null)
			throw new UnsupportedOperationException("Symulation system wasn't set, set it first to run simulation!");
		for (; system.canCreateSimulation();) {
			threadPool.execute(createPlanetThread(system, this));
		}
	}

	private static Runnable createPlanetThread(final SimulationSystem<?> s,
			final ParalelSimulationExecutor ps) {
		return new Runnable() {
			@Override
			public void run() {
				if (!s.canCreateSimulation())
					return;
				ps.addRunning();
				Simulation sim = s.createSimualtion();
				try {
					while (!ps.getEndCondition().satisfies()) {
						sim.makeStep();
						ps.notifyUpdate();
						ps.awaitAll();
					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				ps.removeRunning();
			}
		};
	}

	private void notifyUpdate() {
		updateLock.lock();
		try {
			if (planetsMoved == 0)
				planetsMoved = system.getUnits().size();
			planetsMoved--;
			if (planetsMoved == 0) {
				canRun.signalAll();
				system.fireUpdate();
			}
		} finally {
			updateLock.unlock();
		}
	}

	private void awaitAll() throws InterruptedException {
		updateLock.lock();
		try {
			if (planetsMoved != 0)
				canRun.await();
		} finally {
			updateLock.unlock();
		}
	}

	@Override
	public void stopSimulation() {
		getEndCondition().forceFalse();
		try {
			threadPool.awaitTermination(0, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	private final void addRunning() {
		runningCounterLock.lock();
		running++;
		runningCounterLock.unlock();
	}
	
	private final void removeRunning() {
		runningCounterLock.lock();
		running--;
		runningCounterLock.unlock();
	}

	@Override
	public boolean isRunning() {
		runningCounterLock.lock();
		try {
			return running != 0;
		} finally {
			runningCounterLock.unlock();
		}
	}

	@Override
	public SimulationSystem<?> getSystem() {
		return system;
	}

	@Override
	public SimulationCondition getEndCondition() {
		endConditionLock.lock();
		try {
			return endCondition;
		} finally {
			endConditionLock.unlock();
		}
	}

	@Override
	public void setEndCondition(SimulationCondition cond) {
		endConditionLock.lock();
		try {
			endCondition = cond;
		} finally {
			endConditionLock.unlock();
		}
	}

	@Override
	public void setSimulationSystem(SimulationSystem<?> system) {
		this.system = system;
	}

}
