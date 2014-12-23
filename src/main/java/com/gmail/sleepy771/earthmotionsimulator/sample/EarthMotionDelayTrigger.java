package com.gmail.sleepy771.earthmotionsimulator.sample;

import com.gmail.sleepy771.earthmotionsimulator.recordstreaming.RecordTrigger;

public class EarthMotionDelayTrigger implements RecordTrigger<ParticleSystemRecord> {

	private double delay;
	private double lastEvent = -1.;
	
	public EarthMotionDelayTrigger(double dt) {
		if (dt <= 0.)
			delay = 1000.;
		else
			delay = dt;
	}
	
	public double getTimeDelayInMillis() {
		return delay;
	}
	
	public void setTimeDelayInMillis(double dt) {
		delay = dt;
	}
	
	@Override
	public boolean isInteresting(ParticleSystemRecord record) {
		if (lastEvent < 0 || (record.getTime() - lastEvent) > delay) {
			lastEvent = record.getTime();
			return true;
		}
		return false;
	}

}
