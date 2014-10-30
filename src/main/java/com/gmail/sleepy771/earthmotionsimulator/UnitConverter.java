package com.gmail.sleepy771.earthmotionsimulator;

import Jama.Matrix;

public class UnitConverter {
	public static final double AU2M = 149597870700.;
	public static final double AUPDAY2MPS = 1731456.84;
	
	public static Matrix auToM(Matrix v) {
		return v.times(AU2M);
	}
	
	public static Matrix auPDayToMPS (Matrix v) {
		return v.times(AUPDAY2MPS);
	}
}
