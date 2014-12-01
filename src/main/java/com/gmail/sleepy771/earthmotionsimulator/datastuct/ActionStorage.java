package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface ActionStorage<T extends Record> extends Storage<T> {
	void addPushListener(StorageListener<T> pushListener);
	
	void removePushListener(StorageListener<T> pushListener);
	
	void addPullListener(StorageListener<T> pullListener);
	
	void removePullListener(StorageListener<T> pullListener);
}
