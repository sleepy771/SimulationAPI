package com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.AffineTransformation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Transformation;

public class StackedAffineTransformationBuilder {

	// ZAJEBEM PLYN!!!
	
	private List<AffineTransformation> tList;
	private int dim;
	
	public StackedAffineTransformationBuilder(int dim) {
		if (dim <= 0)
			throw new IllegalArgumentException("Dimnsion have to be natural number");
		this.dim = dim;
		tList = new ArrayList<>();
	}
	
	public StackedAffineTransformationBuilder(Transformation... trans) {
		this(Arrays.asList(trans));
	}
	
	public StackedAffineTransformationBuilder(List<Transformation> trans) {
		dim = -1;
		tList = new ArrayList<>();
		for (Transformation t : trans) {
			if (dim < 0) {
				dim = t.dimension();
			}
			checkDimension(t);
			tList.add(new ImprovedAffineTransformation(t));
		}
	}
	
	public StackedAffineTransformationBuilder addLeft(Transformation t) {
		checkDimension(t);
		tList.add(0, new ImprovedAffineTransformation(t));
		return this;
	}
	
	public StackedAffineTransformationBuilder addRight(Transformation t) {
		checkDimension(t);
		tList.add(new ImprovedAffineTransformation(t));
		return this;
	}
	
	public AffineTransformation get(int idx) {
		return tList.get(idx);
	}
	
	public AffineTransformation set(int idx, Transformation t) {
		checkDimension(t);
		return tList.set(idx, new ImprovedAffineTransformation(t));
	}
	
	private void checkDimension(Transformation t) {
		if (dim != t.dimension())
			throw new IllegalArgumentException("Wrong dimension for transformation, expected: " + dim + "given: " + t.dimension());
	}
	
	public AffineTransformation build() {
		AffineTransformation buildAf = null;
		for (AffineTransformation af : tList) {
			if (buildAf == null) {
				buildAf = af;
				continue;
			}
			buildAf = buildAf.leftTranslation(af);
		}
		return buildAf;
		
	}
	
	public int dimension() {
		return dim;
	}

}
