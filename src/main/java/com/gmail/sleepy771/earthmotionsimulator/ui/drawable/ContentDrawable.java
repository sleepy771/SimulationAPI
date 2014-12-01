package com.gmail.sleepy771.earthmotionsimulator.ui.drawable;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Map.Entry;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.SpaceSimulationRecord;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.Storage;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.StorageListener;
import com.gmail.sleepy771.earthmotionsimulator.datastuct.SynchronizedStorage;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.AffineTransformation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Rotation;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.Scaling;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.TransformationToPlane;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl.AffineTransformationImpl;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl.BaseTransformationToPlane;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl.RotationR3;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl.ScalingR3;
import com.gmail.sleepy771.earthmotionsimulator.ui.transformation.impl.StackedAffineTransformationBuilder;
import com.gmail.sleepy771.earthmotionsimulator.utils.ESMath;

public class ContentDrawable implements Drawable {
	private Object observedObject;
	
	private boolean rotationFertig, scalingFertig, transformationComputed;

	private DrawableString calculating;
	private DrawableLegend legend;
	
	private StackedAffineTransformationBuilder builder;
	
	private TransformationToPlane pPerspective;
	private Scaling scaling;
	private Rotation rotation;
	private AffineTransformation shift;

	private Map<Object, DrawablePlanet> drawables;

	private Storage<SpaceSimulationRecord> storage;

	private Dimension windowDimension;
	private double approxDist;
	
	private ComponentListener cListy; // i just used familiar name for it;

	public ContentDrawable(Dimension w, SynchronizedStorage<SpaceSimulationRecord> s, Object o) {
		storage = s;
		initRotationListener(s);
		observedObject = o;
		this.windowDimension = w;
		approxDist = -1.;
		initScalingListener(s);
		
		scaling = new ScalingR3(1.);
		rotation = new RotationR3();
		shift = new AffineTransformationImpl(3);
		
		builder = new StackedAffineTransformationBuilder(3);
		builder.addRight(rotation).addRight(scaling).addRight(shift);
		builder.addRight(new ScalingR3(1.));
		
		initCalculating();
	}
	
	private void initCalculating() {
		calculating = new DrawableStringImpl("Calculating ...", new Point2D.Double(0, 0), 0.);
	}
	
	private void initRotationListener(final SynchronizedStorage<SpaceSimulationRecord> s) {
		StorageListener<SpaceSimulationRecord> rotListener = new StorageListener<SpaceSimulationRecord>() {
			@Override
			public void onAction(Storage<SpaceSimulationRecord> storage,
					SpaceSimulationRecord element) {
				Matrix p1 = storage.getFirst().getBodies().get(observedObject).getPosition();
				Matrix p2 = element.getBodies().get(observedObject).getPosition();
				if (p1.minus(p2).normF() > p1.normF()) {
					computeRotation(p1, p2);
					// autodestruct itself
					s.removePushListener(this);
				}
			}
			
		};
		s.addPushListener(rotListener);
	}
	
	private void initScalingListener(final SynchronizedStorage<SpaceSimulationRecord> s) {
		StorageListener<SpaceSimulationRecord> scaleListener = new StorageListener<SpaceSimulationRecord>() {
			@Override
			public void onAction(Storage<SpaceSimulationRecord> storage,
					SpaceSimulationRecord element) {
				setDistance(element.getBodies().get(observedObject).getPosition().normF());
				computeScaling();
				s.removePushListener(this);
			}
		};
		s.addPushListener(scaleListener);
	}
	
	// this method force me to use only swing, should rewrite this
	public ComponentListener getResizeListener() {
		if (cListy == null) {
			cListy =  new ComponentListener() {
				@Override
				public void componentShown(ComponentEvent e) {
				}
				
				@Override
				public void componentResized(ComponentEvent e) {
					windowDimension = e.getComponent().getSize();
					computeScaling();
				}
				
				@Override
				public void componentMoved(ComponentEvent e) {
				}
				
				@Override
				public void componentHidden(ComponentEvent e) {
				}
			};
		}
		return cListy;
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (!transformationComputed) {
			calculating.draw(g2d);
			return;
		}
		SpaceSimulationRecord rec = storage.pull();
		for (Entry<Object, Body> entry : rec.getBodies().entrySet()) {
			DrawablePlanet p = this.drawables.get(entry.getKey());
			p.setPosition(pPerspective.asPoint(entry.getValue().getPosition()));
			p.draw(g2d);
		}
		legend.draw(g2d);
	}

	private void computeRotation(Matrix posI, Matrix posF) {
		Matrix pPerp = ESMath.outerProductR3(posI, posF);
		double angle = Math.acos(pPerp.get(2, 0) / pPerp.normF());
		Matrix norm = new Matrix(3, 1);
		norm.set(0, 0, -pPerp.get(1, 0));
		norm.set(1, 0, pPerp.get(0, 0));
		rotation.setAngle(angle);
		rotation.setAxis(norm);
		
		rotationFertig = true;
		runTransformationBuildProcess();
	}
	
	private void computeScaling() {
		if (approxDist > 0 && windowDimension != null) {
			double minWindowSize = Math.min(windowDimension.getHeight(), windowDimension.getWidth());
			scaling.setScaling(minWindowSize/(4*approxDist));
			shift.setShift(new Matrix(new double[] {windowDimension.getWidth()/2, windowDimension.getHeight()/2, 0.}, 3));
			scalingFertig = true;
			runTransformationBuildProcess();
		}
	}
	
	private void setDistance(double dist) {
		this.approxDist = dist;
	}

	public void setObservedObject(Object o) {
		this.observedObject = o;
	}
	
	public Object getObservedObject() {
		return this.observedObject;
	}
	
	public void put(Object id, DrawablePlanet d) {
		drawables.put(id, d);
	}
	
	public void remove(Object id) {
		drawables.remove(drawables);
	}
	
	private void runTransformationBuildProcess() {
		if (rotationFertig && scalingFertig) {
			if (pPerspective == null) {
				pPerspective = new BaseTransformationToPlane(builder.build());
				transformationComputed = true;
				calculating = null;
			}
			else
				pPerspective.setTransformation(builder.build());
		}
	}

}
