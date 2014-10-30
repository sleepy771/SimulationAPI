package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.geom.Point2D;

import Jama.Matrix;

public class TransformationToSurfaceImpl implements TransformationToSurface {
	
	private final static Matrix TO_SUBSPACE = new Matrix(new double[][] {new double[] {1,0,0}, new double[] {0,1,0}});
	private Matrix transMatrix;
	private Matrix normal;
	private double scaling;
	
	public TransformationToSurfaceImpl() {
		transMatrix = new Matrix(2, 3);
	}

	@Override
	public Matrix transform(Matrix position) {
		return transMatrix.times(position);
	}

	@Override
	public void setTransformMatrix(Matrix m) {
		this.transMatrix = m;
	}

	@Override
	public void setShift(Matrix d) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRotation(Matrix n, double r) {
		this.normal = n;
		double sa = Math.sin(r);
		double ca = Math.cos(r);
		double[] normField = n.getRowPackedCopy();
		Matrix rotMatrix = new Matrix(3,3);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (i == j) {
					rotMatrix.set(i, j, ca + (1 - ca) * normField[i] * normField[j]);
				} else {
					double sinPart = 0.;
					for (int k = 0; k < 3; k++)
						sinPart += leviCivita(i,j,k) * normField[k];
					rotMatrix.set(i, j, (1 - ca) * normField[i] * normField[j] + sinPart * sa);
				}
			}
		}
		transMatrix = TO_SUBSPACE.times(rotMatrix).times(scaling);
	}
	
	private static double leviCivita(int i, int j, int k) {
		if (i == j || j == k || k == i)
			return 0.;
		int r = 0;
		int swp = -1;
		while(true) {
			if (j > i && j < k)
				break;
			r++;
			if (j < i) {
				swp = i;
				i = j;
				j = swp;
			} else if (j > k) {
				swp = k;
				k = j;
				j = swp;
			}
		}
		return r % 2 == 1 ? -1. : 1.;
	}

	@Override
	public boolean isAffine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setScalingOnSurface(double scaling) {
		this.scaling = scaling;
	}

	@Override
	public void setSurfaceNormal(Matrix n) {
		this.normal = n;
		calculateNormalAndAngle(n);
	}

	@Override
	public Point2D transformToSurface(Matrix vec) {
		Matrix trans = transform(vec);
		Point2D p = new Point2D.Double(trans.get(0, 0), trans.get(1, 0));
		return p;
	}
	
	private void calculateNormalAndAngle(Matrix sN) {
		// [0;0;1]
		Matrix normal = new Matrix(new double[] {sN.get(1, 0), -sN.get(0, 0), 0}, 3);
		normal.times(1./normal.normF());
		double angle = Math.acos(sN.get(2, 0)/sN.normF());
		setRotation(normal, angle);
	}

}
