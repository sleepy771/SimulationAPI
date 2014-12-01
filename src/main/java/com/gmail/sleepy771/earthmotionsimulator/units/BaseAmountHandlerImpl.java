package com.gmail.sleepy771.earthmotionsimulator.units;

public class BaseAmountHandlerImpl<T> implements AmountConverter<T> {

	private Unit convertTo;
	
	public BaseAmountHandlerImpl() {
	}
	
	public BaseAmountHandlerImpl(Unit c) {
		convertTo = c;
	}
	
	@Override
	public final void setToUnit(Unit unit) {
		this.convertTo = unit;
	}

	@Override
	public final Unit getToUnit() {
		return convertTo;
	}

	@Override
	public final Amount<T> convertAmount(Amount<T> amount) {
		return amount.convertTo(convertTo);
	}

}
