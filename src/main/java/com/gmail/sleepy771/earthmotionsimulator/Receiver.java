package com.gmail.sleepy771.earthmotionsimulator;

public interface Receiver<T extends Record> {
	void onReceive(T rec);
	
	boolean isListening();
}
