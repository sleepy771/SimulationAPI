package com.gmail.sleepy771.earthmotionsimulator.datastuct;

import java.util.Iterator;
import java.util.LinkedList;

import com.gmail.sleepy771.earthmotionsimulator.Record;
import com.gmail.sleepy771.earthmotionsimulator.UnmodifiableIterator;

public class QueueStorage<T extends Record> implements Storage<T> {

	private LinkedList<T> records;
	
	public QueueStorage() {
		records = new LinkedList<>();
	}
	
	@Override
	public Iterator<T> iterator() {
		return new UnmodifiableIterator<>(records.iterator());
	}

	@Override
	public T pull() {
		if (records.isEmpty())
			return null;
		if (records.size() == 1)
			return records.getFirst();
		return records.removeFirst();
	}

	@Override
	public T getFirst() {
		return records.getFirst();
	}

	@Override
	public T getLast() {
		return records.getLast();
	}

	@Override
	public void push(T record) {
		records.add(record);
	}

	@Override
	public void flush() {
		records.clear();
	}

}
