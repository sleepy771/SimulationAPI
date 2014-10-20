package com.gmail.sleepy771.earthmotionsimulator;

public interface Receiver<T extends Response> {
	void onReceive(T rec);
}
