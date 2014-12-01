package com.gmail.sleepy771.earthmotionsimulator.units;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public abstract class AbstractConversionRule<T extends SystemUnit, R> implements ConversionRule<T, R> {

	private AmountConverter<R> amountHandler;
	private T unit;
	private Unit inUnit;
	
	public AbstractConversionRule(AmountConverter<R> h, Unit u) {
		amountHandler = h;
		inUnit = u;
	}
	
	@Override
	public final void setSystemUnit(T unit) {
		this.unit = unit;
	}
	
	@Override
	public final T getSystemUnit() {
		return unit;
	}
	
	@Override
	public final AmountConverter<R> getHandler() {
		return amountHandler;
	}

	@Override
	public final void setHandler(AmountConverter<R> handler) {
		this.amountHandler = handler;
	}

	@Override
	public final void setInUnit(Unit u) {
		inUnit = u;
	}

	@Override
	public final Unit getInUnit() {
		return inUnit;
	}
	
	@Override
	public final void setConvertTo(Unit u) {
		getHandler().setToUnit(u);
	}
	
	@Override
	public final Unit getConvertTo() {
		return getHandler().getToUnit();
	}

}
