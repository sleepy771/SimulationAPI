package com.gmail.sleepy771.earthmotionsimulator.sample;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.units.ConversionRule;
import com.gmail.sleepy771.earthmotionsimulator.units.Unit;
import com.gmail.sleepy771.earthmotionsimulator.units.UnitConverter;

public class PlanetConverter implements UnitConverter<Body> {

	private final ConversionRule<Body, Double> massRule;
	private final ConversionRule<Body, Matrix> positionRule, momentumRule;
	
	public PlanetConverter(Unit massUnit, Unit lengthUnit, Unit momentumUnit) {
		massRule = BodyMassConversionRule.createRuleWithBaseAmountHandler(massUnit);
		positionRule = BodyPositionConversionRule.createRuleWithBaseAmountHandler(lengthUnit);
		momentumRule = BodyMomentumConversionRule.createRuleWithBaseAmountHandler(momentumUnit);
	}
	
	@Override
	public Planet convertSystemUnit(Body object) {
		massRule.setSystemUnit(object);
		positionRule.setSystemUnit(object);
		momentumRule.setSystemUnit(object);
		return new Planet(object.getId(), massRule.convert().getValue(), momentumRule.convert().getValue(), positionRule.convert().getValue());
	}
	
	public ConversionRule<Body, Double> getMassRule() {
		return massRule;
	}
	
	public ConversionRule<Body, Matrix> getPostionRule() {
		return positionRule;
	}
	
	public ConversionRule<Body, Matrix> getMomentumRule() {
		return momentumRule;
	}
	
	public void setConvertMassTo(Unit u) {
		massRule.setConvertTo(u);
	}
	
	public void setConvertPositionTo(Unit u) {
		positionRule.setConvertTo(u);
	}
	
	public void setConvertMomentumTo(Unit u) {
		momentumRule.setConvertTo(u);
	}

}
