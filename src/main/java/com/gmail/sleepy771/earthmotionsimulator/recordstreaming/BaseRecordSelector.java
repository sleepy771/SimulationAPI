package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public class BaseRecordSelector<R extends Record> extends AbstractRecordSelector<R> {

	private final RecordTrigger<R> trigger;
	
	public BaseRecordSelector(RecordSink<R> sink, RecordTrigger<R> t) {
		super(sink);
		trigger = t;
	}
	
	@Override
	public boolean isInteresting(R record) {
		return trigger.isInteresting(record);
	}

}
