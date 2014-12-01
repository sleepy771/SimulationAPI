package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface StorageListener<T extends Record> {
	void onAction(Storage<T> storage, T element);
}
