package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public class BaseRecordSource<R extends Record> implements RecordSource<R> {
	
	private Lock sourceLock;
	private Set<RecordSink<R>> sinks;
	
	public BaseRecordSource() {
		sourceLock = new ReentrantLock();
		sinks = new HashSet<>();
	}
	
	@Override
	public void addSink(RecordSink<R> sink) {
		sourceLock.lock();
		sinks.add(sink);
		sourceLock.unlock();
	}

	@Override
	public void removeSink(RecordSink<R> sink) {
		sourceLock.lock();
		sinks.add(sink);
		sourceLock.unlock();
	}

	@Override
	public void pushRecord(R record) {
		sourceLock.lock();
		for (RecordSink<R> sink : sinks) {
			sink.onReceive(record);
		}
		sourceLock.unlock();
	}

}
