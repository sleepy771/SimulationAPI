package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface RecordSource<R extends Record> {
	void addSink(RecordSink<R> sink);
	
	void removeSink(RecordSink<R> sink);
	
	void pushRecord(R record);
}
