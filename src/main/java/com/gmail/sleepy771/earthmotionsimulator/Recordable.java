package com.gmail.sleepy771.earthmotionsimulator;

public interface Recordable<R extends Record> {
	R createRecord();
}
