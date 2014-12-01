package com.gmail.sleepy771.earthmotionsimulator;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.objects.Planet;
import com.gmail.sleepy771.earthmotionsimulator.simulation.AbstarctSimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;
import com.gmail.sleepy771.earthmotionsimulator.simulation.impl.ParalelSimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.units.Unit;
import com.gmail.sleepy771.earthmotionsimulator.units.Units;
import com.gmail.sleepy771.earthmotionsimulator.units.planet.PlanetConverter;

public class EarthMotionConfig extends AbstractRunConfiguration implements RunConfiguration {

	private List<Body> bodies;
	private DateTime date;
	private Storage<SpaceSimulationRecord> storage;
	
	@Override
	public void beforeStart() {
		// bodies setup
		PlanetConverter converter = new PlanetConverter(Units.KILOGRAM, Units.ASTRONOMICAL_UNIT, Unit.Composed.KILOGRAM_ASTRONOMICAL_UNIT_PER_DAY);
		converter.getMomentumRule().getHandler().setToUnit(Unit.Composed.KILOGRAM_METER_PER_SECOND);
		converter.getMassRule().getHandler().setToUnit(Units.KILOGRAM);
		converter.getPostionRule().getHandler().setToUnit(Units.METER);
		bodies = new ArrayList<>();
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Sun", 0),  1.3271244004193938E30, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Sun", 0), 1.3271244004193938E30, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Mercury", 1), 3.302E23, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Venus", 2), 48.685E23, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Earth", 3), 5.97219E24, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Mars", 4), 6.4185E23, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Jupiter", 5), 1898.13E24, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Saturn", 6), 5.68319E26, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Uranus", 7), 86.8103E24, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Neptun", 8), 102.41E24, new double[] {}, new double[] {})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Moon", 100), 734.9E20, new double[] {}, new double[] {})));
		date = new DateTime(2014, 9, 14, 0, 0, 0);
		// TODO init storage
	}

	@Override
	public void afterStop() {
		getExecutor().stopSimulation();
		getSystem().flush();
		releasAll();
		bodies.clear();
		bodies = null;
		date = null;
		releasAll();
	}

	@Override
	public SimulationSystem<?> createSystem() {
		return new SolarSystem(date.getMillis(), 6 * 3600000, bodies, storage);
	}

	@Override
	public SimulationExecutor createExceutor() {
		return new ParalelSimulationExecutor();
	}

	@Override
	public SimulationCondition createCondition() {
		return new AbstarctSimulationCondition() {
			@Override
			public boolean condition() {
				return false;
			}
		};
	}

}
