package com.gmail.sleepy771.earthmotionsimulator.units;

public interface UnitConversionClosure {
	double fromBase(double value);
	
	double toBase(double value);
}
