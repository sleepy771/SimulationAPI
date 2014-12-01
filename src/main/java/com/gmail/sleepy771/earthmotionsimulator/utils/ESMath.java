package com.gmail.sleepy771.earthmotionsimulator.utils;

import Jama.Matrix;

public class ESMath {
	public static final Matrix outerProductR3(Matrix vectA, Matrix vectB) {
		if (!(vectA.getRowDimension() == 3 && vectA.getColumnDimension() == 1 && vectB.getRowDimension() == 3 && vectB.getColumnDimension() == 1))
			throw new IllegalArgumentException("Wrong dimension vector should be R3x1 matrices");
		double[] a = vectA.getRowPackedCopy();
		double[] b = vectB.getRowPackedCopy();
		double[] c = new double[] {
			a[1] * b[2] - a[2] * b[1],
			a[2] * b[0] - a[0] * b[2],
			a[0] * b[1] - a[1] * b[0]
		};
		return new Matrix(c, 3);
	}
}
