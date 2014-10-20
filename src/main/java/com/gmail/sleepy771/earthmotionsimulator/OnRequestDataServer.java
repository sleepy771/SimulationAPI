package com.gmail.sleepy771.earthmotionsimulator;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OnRequestDataServer implements DataServer<Void, SpaceSimulationRecord> {

	private SpaceSimulationRecord record;
	private final Lock dataLock;
	private final Condition isNull;
	private final Condition gimmeMoar;
	
	public OnRequestDataServer() {
		dataLock = new ReentrantLock();
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

}
