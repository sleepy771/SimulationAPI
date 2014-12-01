package com.gmail.sleepy771.earthmotionsimulator.units.planet;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.units.AbstractMatrixConversionRule;
import com.gmail.sleepy771.earthmotionsimulator.units.AmountConverter;
import com.gmail.sleepy771.earthmotionsimulator.units.BaseAmountHandlerImpl;
import com.gmail.sleepy771.earthmotionsimulator.units.ConversionRule;
import com.gmail.sleepy771.earthmotionsimulator.units.Unit;
import com.gmail.sleepy771.earthmotionsimulator.units.UnitType;

public class BodyPositionConversionRule extends
		AbstractMatrixConversionRule<Body> implements
		ConversionRule<Body, Matrix> {

	private BodyPositionConversionRule(AmountConverter<Matrix> h, Unit u) {
		super(h, u);
	}

	@Override
	public Matrix getValue(Body unit) {
		return unit.getPosition();
	}

	public static BodyPositionConversionRule createRule(AmountConverter<Matrix> h, Unit u) {
		if (u.getType() != UnitType.COMPOSED) {
			throw new IllegalArgumentException("Unit have to be of type UnitType.COMPOSED");
		}
		return new BodyPositionConversionRule(h, u);
	}
	
	public static BodyPositionConversionRule createRuleWithBaseAmountHandler(Unit u) {
		return BodyPositionConversionRule.createRule(new BaseAmountHandlerImpl<Matrix>(), u);
	}
	
}
