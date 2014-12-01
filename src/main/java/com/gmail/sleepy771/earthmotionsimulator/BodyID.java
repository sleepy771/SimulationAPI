package com.gmail.sleepy771.earthmotionsimulator;

public class BodyID {
	private final String name;
	private final int id;
	
	public BodyID(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String toString() {
		return this.name;
	}
	
	public int hashCode() {
		return 37 * (37 * 17 + this.id) + name.hashCode();
	}
	
	public boolean equals(Object o) {
		if (BodyID.class.isInstance(o)) {
			BodyID p = BodyID.class.cast(o);
			return p.id == this.id && p.name.equals(this.name);
		}
		return false;
	}
}
