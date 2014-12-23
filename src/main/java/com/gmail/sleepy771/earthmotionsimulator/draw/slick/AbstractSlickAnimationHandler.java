package com.gmail.sleepy771.earthmotionsimulator.draw.slick;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.Record;
import com.gmail.sleepy771.earthmotionsimulator.recordstreaming.RecordSink;

public abstract class AbstractSlickAnimationHandler<R extends Record> implements SlickDrawable, RecordSink<R> {

	private LinkedList<R> records;
	private Lock sinkLock;
	
	protected AbstractSlickAnimationHandler() {
		sinkLock = new ReentrantLock();
		records = new LinkedList<>();
	}

	@Override
	public final void onReceive(R record) {
		sinkLock.lock();
		push(record);
		update(record, records);
		sinkLock.unlock();
	}
	
	protected abstract void update(R record, List<R> records);
	
	protected R pop() {
		sinkLock.lock();
		try {
			return records.removeFirst();
		} finally {
			sinkLock.unlock();
		}
	}
	
	protected R pull() {
		sinkLock.lock();
		try {
			return records.getFirst();
		} finally {
			sinkLock.unlock();
		}
	}
	
	protected R popLast() {
		sinkLock.lock();
		try {
			return records.removeLast();
		} finally {
			sinkLock.unlock();
		}
	}
	
	protected R pullLast() {
		sinkLock.lock();
		try {
			return records.getLast();
		} finally {
			sinkLock.unlock();
		}
	}
	
	protected void pushFirst(R record) {
		sinkLock.lock();
		records.addFirst(record);
		sinkLock.unlock();
	}
	
	protected void push(R record) {
		sinkLock.lock();
		records.addLast(record);
		sinkLock.unlock();
	}
	
	protected boolean isEmpty() {
		sinkLock.lock();
		try {
			return records.isEmpty();
		} finally {
			sinkLock.unlock();
		}
	}

}
