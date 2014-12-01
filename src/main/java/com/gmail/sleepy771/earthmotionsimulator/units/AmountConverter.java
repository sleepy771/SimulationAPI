package com.gmail.sleepy771.earthmotionsimulator.units;

public interface AmountConverter<T> {
	void setToUnit(Unit unit);
	
	Unit getToUnit();
	
	Amount<T> convertAmount(Amount<T> amount);
}
