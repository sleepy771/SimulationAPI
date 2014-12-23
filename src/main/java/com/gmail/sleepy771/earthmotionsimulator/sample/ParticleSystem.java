package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.joda.time.DateTime;

import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.recordstreaming.RecordSink;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;

public class ParticleSystem implements SimulationSystem<ParticleSystemRecord> {

	private List<Body> particles; // maybe set would be more appropriate in order to check duplicity
	private final Lock systemLock;
	private final Lock cacheLock;
	private final Lock streamLock;
	
	private List<Body> cache;
	
	private double currentTime;
	private double timeDelta;
	
	private Set<RecordSink<ParticleSystemRecord>> sinks; // Toto je tiez hovadina tie generika by tam nemali byt
															// systemu musi byt jedno, ake sinky pouziva
	
	private ParticleSystem (Collection<? extends Body> cb, double dt, DateTime t) { // jednoznacne factory
		particles = new LinkedList<Body>(cb);
		systemLock = new ReentrantLock();
		cacheLock = new ReentrantLock();
		streamLock = new ReentrantLock();
		currentTime = t.getMillis();
		timeDelta = dt;
	}
	
	public ParticleSystem (Collection<? extends Body> cb, DateTime t, double dt, Collection<? extends RecordSink<ParticleSystemRecord>> cs) {
		this(cb, dt, t);
		sinks = new HashSet<>(cs);
	}
	
	public ParticleSystem(Collection<? extends Body> cb, DateTime t, double dt) {
		this(cb, dt, t);
		sinks = new HashSet<>();
	}
	
	public List<Body> getParticles() {
		systemLock.lock();
		try {
			return particles;
		} finally {
			systemLock.unlock();
		}
	}
	
	public void cacheParticle(Body b) {
		cacheLock.lock();
		getCache().add(b);
		notifyUpdate();
		cacheLock.unlock();
	}
	
	private List<Body> getCache() {
		cacheLock.lock();
		try {
			if (cache == null)
				cache = new LinkedList<>();
			return cache;
		} finally {
			cacheLock.unlock();
		}
	}
	
	public double getCurrentTime() {
		systemLock.lock();
		try {
			return currentTime;
		} finally {
			systemLock.unlock();
		}
	}
	
	private void notifyUpdate() {
		cacheLock.lock();
		if (getCache().size() == particles.size())
			update();
		cacheLock.unlock();
	}
	
	@Override
	public void update() {
		systemLock.lock();
		cacheLock.lock();
		pushRecord(createRecord());
		particles = cache;
		cache = null;
		currentTime += timeDelta;
		cacheLock.unlock();
		systemLock.unlock();
	}
	
	@Override
	public ParticleSystemRecord createRecord() {
		systemLock.lock();
		try {
			return new ParticleSystemRecord(getParticles(), getCurrentTime());
		} finally {
			systemLock.unlock();
		}
	}

	@Override
	public void addSink(RecordSink<ParticleSystemRecord> sink) {
		streamLock.lock();
		sinks.add(sink);
		streamLock.unlock();
	}

	@Override
	public void removeSink(RecordSink<ParticleSystemRecord> sink) {
		streamLock.lock();
		sinks.remove(sink);
		streamLock.unlock();
	}

	@Override
	public void pushRecord(ParticleSystemRecord record) {
		streamLock.lock();
		for (RecordSink<ParticleSystemRecord> s : sinks) {
			s.onReceive(record);
		}
		streamLock.unlock();
	}

}
