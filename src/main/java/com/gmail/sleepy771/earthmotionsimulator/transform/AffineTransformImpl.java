package com.gmail.sleepy771.earthmotionsimulator.transform;

import Jama.Matrix;

public class AffineTransformImpl implements AffineTransform {

	private final static double EPSILON = 1e-14;
	
//	enum TransformationType {
//		IDENTITY, DIAGONAL, REGULAR, SINGULAR, ORTHOGONAL;
//		
//		
//	}
	
	private Matrix trans;
	private Matrix shift;
//	private TransformationType type;
	private int from, to;
	
	public AffineTransformImpl(int from, int to) {
		this.from = from;
		this.to = to;
		
	}
	
	public AffineTransformImpl(Matrix t, Matrix s) {
		from = t.getColumnDimension();
		to = t.getRowDimension();
		checkDimension(s);
		trans = t;
		shift = s;
	}
	
	@Override
	public Matrix transform(Matrix v) {
		return trans.times(v).plusEquals(shift);
	}

	@Override
	public void setShift(Matrix s) {
		checkDimension(s);
		this.shift = s;
	}

	@Override
	public void setTransformation(Matrix m) {
		checkDimensionOfTransformation(m);
		this.trans = m;
	}

	@Override
	public void setMatrix(Matrix af) {
		Matrix trans = af.getMatrix(0, af.getRowDimension() - 2, 0, af.getColumnDimension() - 2);
		Matrix shift = af.getMatrix(0, af.getRowDimension() - 2, af.getColumnDimension() - 1, af.getColumnDimension() - 1);
		checkDimensionOfTransformation(trans);
		checkDimension(shift);
	}

	@Override
	public int getDomainDim() {
		return from;
	}

	@Override
	public int getCodomainDim() {
		return to;
	}

	@Override
	public Matrix getTransformation() {
		return trans;
	}

	@Override
	public Matrix getShift() {
		return shift;
	}

	@Override
	public Matrix getAugumentedMatrix() {
		Matrix aug = new Matrix(from + 1, to + 1);
		aug.setMatrix(0, to - 1, 0, from - 1, trans);
		aug.setMatrix(0, from - 1, to, to, shift);
		aug.set(from, to, 1);
		return aug;
//		return new Matrix();
	}

	@Override
	public AffineTransform leftTranslation(AffineTransform af) {
		return new AffineTransformImpl(trans.times(af.getTransformation()), trans.times(af.getShift()).plusEquals(shift));
	}
	
	private void checkDimension(Matrix m) {
		if (m.getRowDimension() != to) {
			throw new IllegalArgumentException("Dimension mismatch!");
		}
	}
	
	private void checkDimensionOfTransformation(Matrix m) {
		if (m.getColumnDimension() != from && m.getRowDimension() != to) {
			throw new IllegalArgumentException("Dimension mismatch!");
		}
	}

	@Override
	public boolean isInvertible() {
		if (from != to)
			return false;
		return trans.det() > EPSILON;
	}

	@Override
	public AffineTransform inverse() {
		if (!isInvertible())
			throw new UnsupportedOperationException("This transformation is not invertible");
		Matrix inv = trans.inverse();
		return new AffineTransformImpl(inv, inv.times(shift).times(-1));
	}
	
	public static AffineTransform createIdentity(int dim) {
		return new AffineTransformImpl(Matrix.identity(dim, dim), new Matrix(dim, 1));
	}
	
	public static AffineTransform createScaling(int dim, double scaling) {
		return new AffineTransformImpl(Matrix.identity(dim, dim).times(scaling), new Matrix(dim, 1));
	}
	
	public static AffineTransform createRotationR3(double angle, Matrix n) {
		n = n.times(1./n.normF());
		double[] nvals = n.getRowPackedCopy();
		double[][] rot = new double[3][3];
		double ca = Math.cos(angle);
		double sa = Math.sin(angle);
		for (int i=0; i<3; i++) {
			for (int j = 0; j<3; j++) {
				rot[j][i] = (1 - ca) * nvals[i] * nvals[j] - leviCiviteSum(i, j, nvals) * sa;
				if (i == j)
					rot[i][i] += ca;
			}
		}
		return new AffineTransformImpl(new Matrix(rot), new Matrix(3,1));
	}
	
	protected static double leviCiviteSum(int i, int j, double[] nvals) {
		if (i == j)
			return 0;
		int k = ((i + 1) ^ (j + 1)) - 1;
		return Math.signum(j - i) * Math.signum(k - j) * Math.signum(k - i) * nvals[k];
	}
	
	public static AffineTransform createShift(double... bs) {
		int dim = bs.length;
		return new AffineTransformImpl(Matrix.identity(dim, dim), new Matrix(bs, dim));
	}

}
