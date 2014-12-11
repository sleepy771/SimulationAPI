package com.gmail.sleepy771.earthmotionsimulator.sample;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.units.AbstractNumberConverionRule;
import com.gmail.sleepy771.earthmotionsimulator.units.AmountConverter;
import com.gmail.sleepy771.earthmotionsimulator.units.BaseAmountHandlerImpl;
import com.gmail.sleepy771.earthmotionsimulator.units.ConversionRule;
import com.gmail.sleepy771.earthmotionsimulator.units.Unit;
import com.gmail.sleepy771.earthmotionsimulator.units.UnitType;

public class BodyMassConversionRule extends AbstractNumberConverionRule<Body>
		implements ConversionRule<Body, Double> {
	
	private BodyMassConversionRule(AmountConverter<Double> h, Unit u) {
		super(h, u);
	}

	@Override
	public Double getValue(Body unit) {
		return unit.getMass();
	}
	
	public static BodyMassConversionRule createRule(AmountConverter<Double> h, Unit u) {
		if (u.getType() != UnitType.MASS) {
			throw new IllegalArgumentException("Unit have to be of type UnitType.MASS");
		}
		return new BodyMassConversionRule(h, u);
	}
	
	public static BodyMassConversionRule createRuleWithBaseAmountHandler(Unit u) {
		return BodyMassConversionRule.createRule(new BaseAmountHandlerImpl<Double>(), u);
	}

}
