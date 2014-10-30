package com.gmail.sleepy771.earthmotionsimulator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OnRequestDataServer implements DataServer<Void, SpaceSimulationRecord> {

	private SpaceSimulationRecord record;
	private final Lock dataLock;
	private final Lock executorLock;
	private final Condition isNull;
	private final Condition gimmeMoar;
	private SimulationExecutor executor;
	
	public OnRequestDataServer() {
		dataLock = new ReentrantLock();
		executorLock = new ReentrantLock();
		isNull = dataLock.newCondition();
		gimmeMoar = dataLock.newCondition();
		record = null;
	}

	@Override
	public SpaceSimulationRecord get(Request<Void> request) throws InterruptedException {
		dataLock.lock();
		try {
			if (record == null)
				gimmeMoar.signalAll();
			while (record == null)
				isNull.await();
			return record;
		} finally {
			record = null;
			dataLock.unlock();
		}
	}
	
	public void insertRecord(SpaceSimulationRecord rec) {
		dataLock.lock();
		try {
			while(record != null)
				gimmeMoar.await();
			this.record = rec;
			isNull.signalAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			dataLock.unlock();
		}
	}
	
	public void setSimulationExecutor(SimulationExecutor executor) {
		executorLock.lock();
		try {
			this.executor = executor;
		} finally {
			executorLock.unlock();
		}
	}

	@Override
	public boolean isListening() {
		executorLock.lock();
		try {
			return executor == null ? false : executor.isRunning();
		} finally {
			executorLock.unlock();
		}
	}

}
