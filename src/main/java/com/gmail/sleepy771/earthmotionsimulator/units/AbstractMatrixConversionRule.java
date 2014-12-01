package com.gmail.sleepy771.earthmotionsimulator.units;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public abstract class AbstractMatrixConversionRule<T extends SystemUnit> extends AbstractConversionRule<T, Matrix> implements ConversionRule<T, Matrix> {
	
	public AbstractMatrixConversionRule(AmountConverter<Matrix> h, Unit u) {
		super(h, u); // 1
	}
	
	public final Amount<Matrix> convert() {
		return getHandler().convertAmount(asAmount());
	}
	
	public final Amount<Matrix> asAmount() {
		return new Amount.MatrixAmount(getInUnit(), getValue(getSystemUnit()));
	}
}
