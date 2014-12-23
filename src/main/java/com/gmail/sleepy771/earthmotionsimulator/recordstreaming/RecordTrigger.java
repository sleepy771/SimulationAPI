package com.gmail.sleepy771.earthmotionsimulator.recordstreaming;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface RecordTrigger<R extends Record> {
	boolean isInteresting(R record);
}
