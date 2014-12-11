package com.gmail.sleepy771.earthmotionsimulator;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UnmodifiableIterator<T> implements Iterator<T> {
	
	private final Iterator<T> innerIterator;
	private final Lock iterLock;
	
	public UnmodifiableIterator(Iterator<T> iter) {
		innerIterator = iter;
		iterLock = new ReentrantLock();
	}
	
	@Override
	public boolean hasNext() {
		iterLock.lock();
		try {
			return innerIterator.hasNext();
		} finally {
			iterLock.unlock();
		}
	}

	@Override
	public T next() {
		iterLock.lock();
		try {
			return innerIterator.next();
		} finally {
			iterLock.unlock();
		}
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
