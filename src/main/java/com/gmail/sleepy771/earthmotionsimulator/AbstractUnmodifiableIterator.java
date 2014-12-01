package com.gmail.sleepy771.earthmotionsimulator;

import java.util.Iterator;

public abstract class AbstractUnmodifiableIterator<E, K> implements Iterator<E> {

	private final Iterator<K> innerIterator;
	
	public AbstractUnmodifiableIterator(Iterator<K> iterator) {
		innerIterator = iterator;
	}
	
	protected Iterator<K> getIterator() {
		return innerIterator;
	}
	
	@Override
	public boolean hasNext() {
		return innerIterator.hasNext();
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException("Unable to remove with this iterator");
	}

}
