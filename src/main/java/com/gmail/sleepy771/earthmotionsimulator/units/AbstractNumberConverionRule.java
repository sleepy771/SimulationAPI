package com.gmail.sleepy771.earthmotionsimulator.units;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public abstract class AbstractNumberConverionRule<T extends SystemUnit> extends AbstractConversionRule<T, Double> implements ConversionRule<T, Double> {

	public AbstractNumberConverionRule(AmountConverter<Double> h, Unit u) {
		super(h, u); // 1
	}
	
	@Override
	public final Amount<Double> convert() {
		return getHandler().convertAmount(asAmount());
	}

	@Override
	public final Amount<Double> asAmount() {
		return new Amount.NumberAmount(getInUnit(), getValue(getSystemUnit()));
	}

}
