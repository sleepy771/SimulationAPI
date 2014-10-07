package com.gmail.sleepy771.earthmotionsimulator;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ParalelSimulationExecutor implements SimulationExecutor {

	private SimulationSystem<?> s;
	private ExecutorService threadPool;
	private final Lock updateLock;
	private final Lock runningCounterLock;
	private final Condition canRun;
	private int planetsMoved;
	private int running;

	public ParalelSimulationExecutor(SimulationSystem<?> sys) {
		threadPool = Executors.newCachedThreadPool();
		this.s = sys;
		running = 0;
		this.updateLock = new ReentrantLock();
		canRun = updateLock.newCondition();
		runningCounterLock = new ReentrantLock();
	}

	@Override
	public void runSimulation() {
		for (; s.canCreateSimulation();) {
			threadPool.execute(createPlanetThread(s, this));
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
					while (s.getEndCondition().satisfies()) {
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
				planetsMoved = s.getUnits().size();
			planetsMoved--;
			if (planetsMoved == 0) {
				canRun.signalAll();
				s.fireUpdate();
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
		s.getEndCondition().forceFalse();
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
		return s;
	}

}
