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
	private int unitsInSystem;
	private int running;

	public ParalelSimulationExecutor() {
		this(null);
	}

	public ParalelSimulationExecutor(SimulationSystem<?> sys) {
		threadPool = Executors.newCachedThreadPool();
		this.system = sys;
		if (sys != null)
			unitsInSystem = sys.getUnits().size();
		running = 0;
		this.updateLock = new ReentrantLock();
		canRun = updateLock.newCondition();
		runningCounterLock = new ReentrantLock();
		endConditionLock = new ReentrantLock();
	}

	@Override
	public void runSimulation() {
		if (this.system == null)
			throw new UnsupportedOperationException(
					"Simulation system wasn't set, set it first to run simulation!");
		for (; system.canCreateSimulation();) {
			System.out.println("creating simulation");
			threadPool.execute(createPlanetThread(system.createSimualtion(),
					this));
		}
	}

	private static Runnable createPlanetThread(final Simulation s,
			final ParalelSimulationExecutor ps) {
		return new Runnable() {
			@Override
			public void run() {
				ps.addRunning();
				try {
					int stepCount = 0;
					while (ps.getEndCondition().satisfies()) {
						System.out.println("new step");
						s.makeStep();
						System.out.println("did step " + stepCount++);
						ps.notifyUpdate(stepCount - 1);
						System.out.println("update notified");
					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				}
				ps.removeRunning();
			}
		};
	}

	private void notifyUpdate(int step) throws InterruptedException {
		updateLock.lock();
		if (planetsMoved == 0) {
			planetsMoved = unitsInSystem;
		}
		planetsMoved--;
		if (planetsMoved == 0) {
			System.out.println("update fired!! " + step);
			canRun.signalAll();
			system.performUpdate();
		} else {
			canRun.await();
		}
		updateLock.unlock();
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
		unitsInSystem = system.getUnits().size();
	}

}
