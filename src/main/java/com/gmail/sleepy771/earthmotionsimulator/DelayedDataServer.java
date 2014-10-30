package com.gmail.sleepy771.earthmotionsimulator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DelayedDataServer implements DataServer<Void, SpaceSimulationRecord> {

	private final BlockingQueue<SpaceSimulationRecord> recordQueue;
	private SimulationExecutor executor;
	private final Lock queueLock;
	private double recordTimeDelta;
	
	public DelayedDataServer(int maxSize, double timeDelta, BlockingQueue<SpaceSimulationRecord> recordQueue) {
		this.recordQueue = recordQueue;
		this.queueLock = new ReentrantLock();
		recordTimeDelta = timeDelta;
	}
	
	@Override
	public SpaceSimulationRecord get(Request<Void> reques)
			throws InterruptedException {
		return recordQueue.take();
	}

	@Override
	public void insertRecord(SpaceSimulationRecord record) {
			if (record.getTime() + recordTimeDelta > record.getTime())
				return;
			try {
				recordQueue.put(record);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void setExecutor(SimulationExecutor executor) {
		queueLock.lock();
		try {
			this.executor = executor;
		} finally {
			queueLock.unlock();
		}
	}

	@Override
	public boolean isListening() {
		queueLock.lock(); // TODO maybe other lock
		try {
			return executor == null ? false : executor.isRunning();
		} finally {
			queueLock.unlock();
		}
	}

}
