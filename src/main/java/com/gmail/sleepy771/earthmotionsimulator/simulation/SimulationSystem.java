package com.gmail.sleepy771.earthmotionsimulator.simulation;

import com.gmail.sleepy771.earthmotionsimulator.Record;
import com.gmail.sleepy771.earthmotionsimulator.Recordable;
import com.gmail.sleepy771.earthmotionsimulator.recordstreaming.RecordSource;

public interface SimulationSystem<R extends Record> extends Recordable<R>, RecordSource<R> {
	void update();
}
