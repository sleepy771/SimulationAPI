package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.gmail.sleepy771.earthmotionsimulator.AbstractRunConfiguration;
import com.gmail.sleepy771.earthmotionsimulator.BodyID;
import com.gmail.sleepy771.earthmotionsimulator.RunConfiguration;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.SynchronizedStorage;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.simulation.AbstarctSimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationCondition;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.simulation.SimulationSystem;
import com.gmail.sleepy771.earthmotionsimulator.simulation.impl.ParalelSimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.units.Unit;
import com.gmail.sleepy771.earthmotionsimulator.units.Units;

public class EarthMotionConfig extends AbstractRunConfiguration<Body, ParticleSystemRecord> implements RunConfiguration {

	private List<Body> bodies;
	private DateTime date;
	
	@Override
	public void beforeStart() {
		// bodies setup
		PlanetConverter converter = new PlanetConverter(Units.KILOGRAM, Units.ASTRONOMICAL_UNIT, Unit.Composed.KILOGRAM_ASTRONOMICAL_UNIT_PER_DAY);
		converter.getMomentumRule().getHandler().setToUnit(Unit.Composed.KILOGRAM_METER_PER_SECOND);
		converter.getMassRule().getHandler().setToUnit(Units.KILOGRAM);
		converter.getPostionRule().getHandler().setToUnit(Units.METER);
		bodies = new ArrayList<>();
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Sun", 0),  1.3271244004193938E30, new double[] {1.142040439574852E-03, 5.472638374274859E-05, -1.391470641511653E-04}, new double[] {2.018456216623680E-04, -4.644973878159391E-03, -1.702282443840230E-04})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Mercury", 1), 3.302E23, new double[] {-2.286960635011969E-02, 2.248147382563623E-02, 3.896303897556996E-03}, new double[] {2.401859749134659E-01, 2.041578632920904E-01, -5.127302534867740E-03})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Venus", 2), 48.685E23, new double[] {8.931853148841771E-03, -1.864495968001614E-02, -8.449789347503884E-04}, new double[] {-6.630573433375805E-01, -2.872503675581860E-01, 3.423268716554650E-02})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Earth", 3), 5.97219E24, new double[] {-6.763168158278883E-03, 1.541758230939028E-02, -1.401410798416276E-04}, new double[] {8.929094222092794E-01, 4.368856823025477E-01, -1.851312314686687E-04})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Mars", 4), 6.4185E23, new double[] {1.347432417822442E-02, 8.776613021522082E-03, -2.590906167547583E-04}, new double[] {7.528727902917255E-01, -1.185711059155637E+00, -4.339072269900958E-02})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Jupiter", 5), 1898.13E24, new double[] {-4.843432717459434E-03, -4.310200747812852E-03, 1.292498963714494E-05}, new double[] {-3.310657732971043E+00, 4.129381121206874E+00, 5.674662577367864E-02})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Saturn", 6), 5.68319E26, new double[] {5.394290702745575E-03, -3.177692166500975E-03, -2.525792221379757E-04}, new double[] {-5.722851150955076E+00, -8.124442964349832E+00, 3.688310294919126E-01})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Uranus", 7), 86.8103E24, new double[] {1.288915808216630E-04, 3.675707983459846E-03, -1.124341749841618E-04}, new double[] {1.937977829339631E+01, 4.982904616518505E+00, -2.326225619609057E-01})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Neptun", 8), 102.41E24, new double[] {2.379524425764417E-03, 2.942301634622775E-03, -2.276325664323270E-04}, new double[] {2.743751240807494E+01, -1.205869717125863E+01, -3.841639678360456E-01})));
		bodies.add(converter.convertSystemUnit(new Planet(new BodyID("Moon", 100), 734.9E20, new double[] {-6.926045972449682E-03, 1.488048388312171E-02, -9.805102242759926E-05}, new double[] {8.903483406634283E-01, 4.377122662239437E-01, -3.349330904213200E-04})));
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
	protected SimulationSystem<Body> createSystem() {
		return new ParticleSystem(date.getMillis(), 6 * 3600000, bodies, getStorage());
	}

	@Override
	protected SimulationExecutor createExceutor() {
		return new ParalelSimulationExecutor();
	}

	@Override
	protected SimulationCondition createCondition() {
		return new AbstarctSimulationCondition() {
			
			private double startTime = ((ParticleSystem) getSystem()).getSimulationTime();
			private ParticleSystem sys = (ParticleSystem) getSystem();
			private final static double YEAR = 365. * 24 * 3600000;
			
			@Override
			public boolean condition() {
				System.out.println("Condition");
				return sys.getSimulationTime() - startTime < YEAR;
			}
		};
	}

	@Override
	public Storage<ParticleSystemRecord> createStorage() {
		return new SynchronizedStorage<>();
	}
	
	public SynchronizedStorage<ParticleSystemRecord> getStorageWithListeners() {
		return (SynchronizedStorage<ParticleSystemRecord>) getStorage();
	}

}
