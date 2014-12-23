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
	
	public static final boolean approxEquals(Matrix u, Matrix v, double err) {
		Matrix comp = u.minus(v);
		double[] columnPackedComparisonMatrix = comp.getColumnPackedCopy();
		for (int k=0; k < columnPackedComparisonMatrix.length; k++)
			if (Math.abs(columnPackedComparisonMatrix[k]) > err)
				return false;
		return true;
	}
	
	public static String toString(Matrix m) {
		StringBuilder sb = new StringBuilder("[");
		int rows = m.getRowDimension();
		int columns = m.getColumnDimension();
		for (int r = 0; r < rows; r++) {
			sb.append("[");
			for (int c = 0; c < columns; c++) {
				sb.append(m.get(r, c));
				if (c < columns)
					sb.append(", ");
			}
			sb.append("]");
			if (r < rows-1)
				sb.append(";\n");
			else
				sb.append("]");
		}
		return sb.toString();
	}
}
