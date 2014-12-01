package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.gmail.sleepy771.earthmotionsimulator.Record;
import com.gmail.sleepy771.earthmotionsimulator.UnmodifiableIterator;

public class SynchronizedStorage<T extends Record> implements ActionStorage<T> {

	private Set<StorageListener<T>> pullListeners;
	private Set<StorageListener<T>> pushListeners;
	private Storage<T> innerstorage;
	private Lock storageLock;
	
	public SynchronizedStorage() {
		pullListeners = new HashSet<>();
		pushListeners = new HashSet<>();
		innerstorage = new QueueStorage<>();
		storageLock = new ReentrantLock();
	}
	
	@Override
	public T pull() {
		storageLock.lock();
		T element = innerstorage.pull();
		try {
			return element;
		} finally {
			runListners(pushListeners, element);
			storageLock.unlock();
		}
	}
	
	private void runListners(Set<StorageListener<T>> listeners, T element) {
		for (StorageListener<T> listener : listeners) {
			listener.onAction(this, element);
		}
	}

	@Override
	public T getFirst() {
		storageLock.lock();
		try {
			return innerstorage.getFirst();
		} finally {
			storageLock.unlock();
		}
	}

	@Override
	public T getLast() {
		storageLock.lock();
		try {
			return innerstorage.getLast();
		} finally {
			storageLock.unlock();
		}
	}

	@Override
	public void push(T record) {
		storageLock.lock();
		innerstorage.push(record);
		runListners(pushListeners, record);
		storageLock.unlock();
	}

	@Override
	public void flush() {
		storageLock.lock();
		innerstorage.flush();
		storageLock.unlock();
	}

	@Override
	public Iterator<T> iterator() {
		return new UnmodifiableIterator<>(innerstorage.iterator());
	}

	@Override
	public void addPushListener(StorageListener<T> pushListener) {
		pushListeners.add(pushListener);
	}

	@Override
	public void removePushListener(StorageListener<T> pushListener) {
		pushListeners.remove(pushListener);
	}

	@Override
	public void addPullListener(StorageListener<T> pullListener) {
		pullListeners.add(pullListener);
	}

	@Override
	public void removePullListener(StorageListener<T> pullListener) {
		pullListeners.remove(pullListener);
	}

}
