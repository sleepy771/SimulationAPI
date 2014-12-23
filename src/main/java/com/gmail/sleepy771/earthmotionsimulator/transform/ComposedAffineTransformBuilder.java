package com.gmail.sleepy771.earthmotionsimulator.transform;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import com.gmail.sleepy771.earthmotionsimulator.Builder;

public class ComposedAffineTransformBuilder implements Builder<AffineTransform> {

	private LinkedList<AffineTransform> list;
	
	public ComposedAffineTransformBuilder() {
		list = new LinkedList<>();
	}
	
	public ComposedAffineTransformBuilder(AffineTransform af) {
		this();
		list.add(af);
	}
	
	public ComposedAffineTransformBuilder(List<AffineTransform> af) {
		list = new LinkedList<>(af);
	}
	
	public ComposedAffineTransformBuilder(AffineTransform... afs) {
		this(Arrays.asList(afs));
	}
	
	public ComposedAffineTransformBuilder addLeft(AffineTransform af) {
		list.addFirst(af);
		return this;
	}
	
	public ComposedAffineTransformBuilder addRight(AffineTransform af) {
		list.addLast(af);
		return this;
	}
	
	public ComposedAffineTransformBuilder add(int idx, AffineTransform af) {
		list.add(idx, af);
		return this;
	}
	
	public ComposedAffineTransformBuilder removeRight() {
		list.removeLast();
		return this;
	}
	
	public ComposedAffineTransformBuilder removeLeft() {
		list.removeFirst();
		return this;
	}
	
	public ComposedAffineTransformBuilder remove(int idx) {
		list.remove(idx);
		return this;
	}
	
	public ComposedAffineTransformBuilder remove(AffineTransform af) {
		list.remove(af);
		return this;
	}
	
	@Override
	public AffineTransform build() {
		AffineTransform outAf = null;
		for (AffineTransform af : list) {
			if (outAf == null) {
				outAf = af;
				continue;
			}
			outAf = outAf.leftTranslation(af);
		}
		return outAf;
	}

}
