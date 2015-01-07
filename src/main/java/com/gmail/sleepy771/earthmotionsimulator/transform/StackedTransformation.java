package com.gmail.sleepy771.earthmotionsimulator.transform;

import java.util.List;

import Jama.Matrix;

public class StackedTransformation implements AffineTransform {

	private List<AffineTransform> transforms;
	
	
	public void addTrasform(AffineTransform af) {
		transforms.add(af);
	}
	
	public void addLeft(AffineTransform af) {
		
	}
	
	public void addRight(AffineTransform af) {
		
	}
	
	public void removeTransform(AffineTransform af) {
		transforms.remove(af);
	}
	
	public AffineTransform get(int idx) {
		return transforms.get(idx);
	}
	
	@Override
	public Matrix transform(Matrix v) {
		for (AffineTransform af : transforms) {
			v = af.transform(v);
		}
		return v;
	}

	@Override
	public void setShift(Matrix s) {
		throw new UnsupportedOperationException("This operation is not supported, for this implmentation");
	}

	@Override
	public void setTransformation(Matrix m) {
		throw new UnsupportedOperationException("This operation is not supported, for this implmentation");
	}

	@Override
	public void setMatrix(Matrix af) {
		throw new UnsupportedOperationException("This operation is not supported, for this implmentation");
	}

	@Override
	public int getDomainDim() {
		return transforms.get(transforms.size() - 1).getDomainDim();
	}

	@Override
	public int getCodomainDim() {
		return transforms.get(0).getCodomainDim();
	}

	@Override
	public Matrix getTransformation() {
		Matrix t = null;
		for (AffineTransform af: transforms) {
			if (t == null) {
				t = af.getTransformation();
				continue;
			}
			t = t.times(af.getTransformation());
		}
		return t;
	}

	@Override
	public Matrix getShift() {
			Matrix tmat = null;
			Matrix sh = null;
			for (AffineTransform af : transforms) {
				if (tmat == null && sh == null) {
					tmat = af.getTransformation();
					sh = af.getShift().copy();
					continue;
				}
				sh.plusEquals(tmat.times(sh));
				tmat = tmat.times(af.getTransformation());
			}
		return sh;
	}

	@Override
	public Matrix getAugumentedMatrix() {
		Matrix aug = null;
		for (AffineTransform af : transforms) {
			if (aug == null) {
				aug = af.getAugumentedMatrix();
				continue;
			}
			aug = aug.times(af.getAugumentedMatrix());
		}
		return null;
	}

	@Override
	public AffineTransform leftTranslation(AffineTransform af) {
		Matrix aug = getAugumentedMatrix().times(af.getAugumentedMatrix());
		AffineTransform ltAf = new AffineTransformImpl(aug.getColumnDimension() - 1, aug.getRowDimension() - 1);
		ltAf.setMatrix(aug);
		return ltAf;
	}

	@Override
	public boolean isInvertible() {
		return Math.abs(getAugumentedMatrix().det()) > 1e-14;
	}

	@Override
	public AffineTransform inverse() {
		Matrix m = getAugumentedMatrix().inverse();
		AffineTransform afInv = new AffineTransformImpl(m.getColumnDimension() - 1, m.getRowDimension() - 1);
		afInv.setMatrix(m);
		return afInv;
	}

	public void setShift(double... ds) {
		throw new UnsupportedOperationException("This operation is not supported, for this implmentation");
	}

}
