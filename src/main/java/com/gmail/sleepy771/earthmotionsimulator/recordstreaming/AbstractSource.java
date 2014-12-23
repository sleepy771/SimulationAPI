package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import java.util.Set;

import com.gmail.sleepy771.earthmotionsimulator.Record;


public abstract class AbstractSource<R extends Record> implements RecordSource<R> {

	private Set<RecordSink<R>> sinks;
	
	@Override
	public final void addSink(RecordSink<R> s) {
		sinks.add(s);
	}

	@Override
	public final void removeSink(RecordSink<R> s) {
		this.sinks.remove(s);
	}

	protected final void pushToStreams(R record) {
		for (RecordSink<R> sink : sinks) {
			sink.onReceive(record);
		}
	}
	
}

