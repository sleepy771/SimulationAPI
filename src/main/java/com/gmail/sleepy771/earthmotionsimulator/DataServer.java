package com.gmail.sleepy771.earthmotionsimulator;

public interface DataServer<R, T extends Response> {
	T get(Request<R> reques) throws InterruptedException;
	
	void insertRecord(T record);
}
