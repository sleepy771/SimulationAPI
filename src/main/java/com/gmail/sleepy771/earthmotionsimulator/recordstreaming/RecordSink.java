package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface RecordSink<R extends Record> {
	void onReceive(R record);
}
