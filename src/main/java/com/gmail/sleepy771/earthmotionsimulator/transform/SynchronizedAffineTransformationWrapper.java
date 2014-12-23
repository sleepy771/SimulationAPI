package com.gmail.sleepy771.earthmotionsimulator.transform;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import Jama.Matrix;

public class SynchronizedAffineTransformationWrapper implements IAffineTransformWrapper {

	private final Lock afLock;
	private AffineTransform innerAf;
	
	public SynchronizedAffineTransformationWrapper(AffineTransform af) {
		innerAf = af;
		afLock = new ReentrantLock();
	}
	
	public SynchronizedAffineTransformationWrapper() {
		this(null);
	}
	
	@Override
	public Matrix transform(Matrix v) {
		afLock.lock();
		try {
			return innerAf.transform(v);
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public void setShift(Matrix s) {
		afLock.lock();
		innerAf.setShift(s);
		afLock.unlock();
	}

	@Override
	public void setTransformation(Matrix m) {
		afLock.lock();
		innerAf.setTransformation(m);
		afLock.unlock();
	}

	@Override
	public void setMatrix(Matrix af) {
		afLock.lock();
		innerAf.setMatrix(af);
		afLock.unlock();
	}

	@Override
	public int getDomainDim() {
		afLock.lock();
		try {
			return innerAf.getDomainDim();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public int getCodomainDim() {
		afLock.lock();
		try {
			return innerAf.getCodomainDim();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public Matrix getTransformation() {
		afLock.lock();
		try {
			return innerAf.getTransformation();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public Matrix getShift() {
		afLock.lock();
		try {
			return innerAf.getShift();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public Matrix getAugumentedMatrix() {
		afLock.lock();
		try {
			return innerAf.getAugumentedMatrix();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public AffineTransform leftTranslation(AffineTransform af) {
		afLock.lock();
		try {
			return innerAf.leftTranslation(af);
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public boolean isInvertible() {
		afLock.lock();
		try {
			return innerAf.isInvertible();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public AffineTransform inverse() {
		afLock.lock();
		try {
			return innerAf.inverse();
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public AffineTransform getAffineTransform() {
		afLock.lock();
		try {
			return innerAf;
		} finally {
			afLock.unlock();
		}
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		afLock.lock();
		innerAf = af;
		afLock.unlock();
	}

	@Override
	public boolean hasInnerTransform() {
		afLock.lock();
		try {
			return innerAf != null;
		} finally {
			afLock.unlock();
		}
	}

}
