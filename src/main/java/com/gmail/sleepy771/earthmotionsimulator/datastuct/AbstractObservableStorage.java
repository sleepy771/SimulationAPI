package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import java.util.Iterator;
import java.util.Observable;

import com.gmail.sleepy771.earthmotionsimulator.Record;


public abstract class AbstractObservableStorage<T extends Record> implements ObservableStorage<T> {

	private Storage<T> storage;
	private Observable observable;
	
	public AbstractObservableStorage(Storage<T> s, Observable o) {
		storage = s;
		observable = o;
	}
	
	@Override
	public T pull() {
		T record = storage.pull();
		try {
			return record;
		} finally {
			if (onPull(record))
				observable.notifyObservers(record);
		}
	}

	@Override
	public T getFirst() {
		return storage.getFirst();
	}

	@Override
	public T getLast() {
		return storage.getLast();
	}

	@Override
	public void push(T record) {
		try {
			storage.push(record);
		} finally {
			if (onPush(record))
				observable.notifyObservers(record);
		}
	}

	@Override
	public void flush() {
		storage.flush();
	}

	@Override
	public Iterator<T> iterator() {
		return storage.iterator();
	}

	@Override
	public void setObservable(Observable o) {
		this.observable = o;
	}

	@Override
	public Observable getObsevable() {
		return observable;
	}
	
	public abstract boolean onPull(T record);
	
	public abstract boolean onPush(T record);
	
}