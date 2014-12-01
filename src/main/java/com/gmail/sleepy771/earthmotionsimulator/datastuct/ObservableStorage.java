package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import java.util.Observable;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface ObservableStorage<T extends Record> extends Storage<T> {
	void setObservable(Observable o);
	
	Observable getObsevable();
}
