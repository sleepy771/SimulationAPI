package com.gmail.sleepy771.earthmotionsimulator;

public class PlanetIdentificator {
	private final String name;
	private final int id;
	
	public PlanetIdentificator(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String toString() {
		return this.name;
	}
}
