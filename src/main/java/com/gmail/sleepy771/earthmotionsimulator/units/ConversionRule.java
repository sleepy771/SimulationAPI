package com.gmail.sleepy771.earthmotionsimulator.units;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public interface ConversionRule<T extends SystemUnit, R> {
	void setSystemUnit(T unit);
	
	T getSystemUnit();
	
	R getValue(T unit);
	
	public Amount<R> convert();
	
	public Amount<R> asAmount();
	
	AmountConverter<R> getHandler();
	
	void setHandler(AmountConverter<R> handler);
	
	void setInUnit(Unit u);
	
	Unit getInUnit();
	
	void setConvertTo(Unit unit);
	
	Unit getConvertTo();
}
