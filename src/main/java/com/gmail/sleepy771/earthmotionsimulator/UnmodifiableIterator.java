package com.gmail.sleepy771.earthmotionsimulator;

import java.util.Iterator;

public class UnmodifiableIterator<T> implements Iterator<T> {
	
	private final Iterator<T> innerIterator;
	
	public UnmodifiableIterator(Iterator<T> iter) {
		innerIterator = iter;
	}
	
	@Override
	public boolean hasNext() {
		return innerIterator.hasNext();
	}

	@Override
	public T next() {
		return innerIterator.next();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
