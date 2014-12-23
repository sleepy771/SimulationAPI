package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public abstract class AbstractRecordSelector<R extends Record> implements RecordTrigger<R>, RecordSink<R> {
	private final RecordSink<R> sink;
	
	protected AbstractRecordSelector(RecordSink<R> s) {
		sink = s;
	}
	
	@Override
	public void onReceive(R record) {
		if (isInteresting(record))
			sink.onReceive(record);
	}

}
