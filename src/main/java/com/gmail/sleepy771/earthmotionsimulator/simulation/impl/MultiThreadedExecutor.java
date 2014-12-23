package com.gmail.sleepy771.earthmotionsimulator.simulation.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.simulation.Simulation;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;

public class MultiThreadedExecutor implements SimulationExecutor {
	
	private ExecutorService executor;
	
	private int runningThreads;
	private Set<Simulation> runningSimulations;
	private Lock threadsLock;
	private CyclicBarrier barrier;

	public MultiThreadedExecutor() {
		executor = Executors.newCachedThreadPool();
		threadsLock = new ReentrantLock();
		runningThreads = 0;
		runningSimulations = new HashSet<>();
	}
	
//	public MultiThreadedExecutor(SimulationSystemUpdate update) {
//		
//	}

	@Override
	public void terminateAll() {
		threadsLock.lock();
		for (Simulation s : runningSimulations) {
			terminate(s);
		}
		threadsLock.unlock();
	}

	@Override
	public void terminate(Simulation s) {
		threadsLock.lock();
		if (isRunning(s)) {
			runningSimulations.remove(s);
		}
		threadsLock.unlock();
	}

	@Override
	public void start() {
		threadsLock.lock();
		if (!isRunning()) {
			barrier = new CyclicBarrier(runningSimulations.size());
			for (Simulation s : runningSimulations) {
				executor.execute(createRunnable(s, barrier, this));
			}
		}
		threadsLock.unlock();
	}

	@Override
	public boolean isRunning(Simulation s) {
		threadsLock.lock();
		try {
			return runningSimulations.contains(s);
		} finally {
			threadsLock.unlock();
		}
	}
	
	private void increaseRunning() {
		threadsLock.lock();
		runningThreads++;
		threadsLock.unlock();
	}
	
	private void decreaseRunning() {
		threadsLock.lock();
		runningThreads--;
		threadsLock.unlock();
	}
	
	private static Runnable createRunnable(final Simulation sim, final CyclicBarrier barrier, final MultiThreadedExecutor mt) {
		return new Runnable() {

			@Override
			public void run() {
				mt.increaseRunning();
				try {
					while (mt.isRunning(sim) && sim.canMakeNextStep()) {
						sim.nextStep();
						barrier.await();
					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				} finally {
					mt.decreaseRunning();
				}
			}
			
		};
	}

	@Override
	public void add(Simulation s) {
		threadsLock.lock();
		if (!isRunning())
			runningSimulations.add(s);
		threadsLock.unlock();
	}

	@Override
	public boolean isRunning() {
		threadsLock.lock();
		try {
			return runningThreads > 0;
		} finally {
			threadsLock.unlock();
		}
	}

	@Override
	public List<Simulation> listAll() {
		threadsLock.lock();
		try {
			return new ArrayList<>(runningSimulations);
		} finally {
			threadsLock.unlock();
		}
	}

}
