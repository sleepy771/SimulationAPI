package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import com.gmail.sleepy771.earthmotionsimulator.Record;

public interface Storage<T extends Record> extends Iterable<T> {
	T pull();
	
	T getFirst();
	
	T getLast();
	
	void push(T record);
	
	void flush();
}
