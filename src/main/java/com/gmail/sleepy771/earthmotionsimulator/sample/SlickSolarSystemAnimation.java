package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.Timer;

import org.newdawn.slick.Graphics;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.draw.slick.AbstractSlickAnimationHandler;
import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickDrawable;
import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickPositionableDrawable;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.transform.AffineTransform;
import com.gmail.sleepy771.earthmotionsimulator.transform.AffineTransformImpl;
import com.gmail.sleepy771.earthmotionsimulator.transform.AffineTransformToPlane;
import com.gmail.sleepy771.earthmotionsimulator.transform.BaseToPlaneTransform;
import com.gmail.sleepy771.earthmotionsimulator.transform.ComposedAffineTransformBuilder;
import com.gmail.sleepy771.earthmotionsimulator.transform.IAffineTransformWrapper;
import com.gmail.sleepy771.earthmotionsimulator.transform.SynchronizedAffineTransformationWrapper;
import com.gmail.sleepy771.earthmotionsimulator.utils.ESMath;

public class SlickSolarSystemAnimation extends AbstractSlickAnimationHandler<ParticleSystemRecord> {
	
	private final ActionListener sceneUpdate = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (!isEmpty() && canDraw()) {
				ParticleSystemRecord rec = pop();
				Map<Object, Point2D> positions = new HashMap<>();
				for (Entry<Object, Body> entry : rec.getBodies().entrySet()) {
					positions.put(entry.getKey(), toPlaneTrans.asPoint(entry.getValue().getPosition()));
				}
				updateParticles(positions);
			}
		}
	};
	
	private Map<Object, SlickPositionableDrawable> particles;
	private Lock animationLock;
	private Lock animLock;
	private Dimension windowSize;
	
	private ComposedAffineTransformBuilder transformBuilder;
	private AffineTransformToPlane toPlaneTrans;
	
	private IAffineTransformWrapper scaling;
	private IAffineTransformWrapper rotation;
	private IAffineTransformWrapper shift;
	
	private Object observedObject;
	
	private Timer animationTimer;
	
	public SlickSolarSystemAnimation(Dimension d, Object particleId) {
		animationTimer = new Timer(20, sceneUpdate);
		particles = new HashMap<>();
		animationLock = new ReentrantLock();
		animLock = new ReentrantLock();
		
		scaling = new SynchronizedAffineTransformationWrapper();
		rotation = new SynchronizedAffineTransformationWrapper();
		shift = new SynchronizedAffineTransformationWrapper();
		
		transformBuilder = new ComposedAffineTransformBuilder(shift, scaling, rotation);
		
		setObservedObjectId(particleId);
		
		setWindowDimension(d);
	}
	
	@Override
	public final void draw(Graphics g) {
		if (!canDraw()) {
			g.drawString("Estimating transformation for drawing purposes!", 50, 50);
			return;
		}
		for (SlickDrawable drawable : getDrawableParticles())
			drawable.draw(g);
	}
	
	public final void setObservedObjectId(Object o) {
		observedObject = o;
	}
	
	public final Object getObservedObjectId() {
		return observedObject;
	}
	
	private boolean canDraw() {
		animationLock.lock();
		try {
			return toPlaneTrans != null;
		} finally {
			animationLock.unlock();
		}
	}

	@Override
	protected final void update(ParticleSystemRecord record,
			List<ParticleSystemRecord> records) {
		computeRotation(records.get(0).getBodies().get(observedObject).getPosition(), record.getBodies().get(observedObject).getPosition());
		runTransformationUpdate();
	}
	
	private void computeRotation(Matrix initPos, Matrix currentPos) {
		if (initPos.normF() > initPos.minus(currentPos).normF() || rotation.hasInnerTransform())
			return;
		Matrix planeNormal = ESMath.outerProductR3(initPos, currentPos);
		planeNormal.timesEquals(1./planeNormal.normF());
		Matrix zAxis = new Matrix(new double[] {0,0,1}, 3);
		Matrix rotationAxis = new Matrix(new double[] {planeNormal.get(1,0), -planeNormal.get(0,0), 0}, 3);
		double angle = Math.acos(planeNormal.get(2, 0));
		
		AffineTransform rotAf = AffineTransformImpl.createRotationR3(angle, rotationAxis);
		if (!ESMath.approxEquals(zAxis, rotAf.transform(planeNormal), 1e-12))
			rotAf = AffineTransformImpl.createRotationR3(-angle, rotationAxis);
		
		rotation.setAffineTransform(rotAf);
	}
	
	private void computeShift() {
		if (shift.hasInnerTransform())
			return;
		shift.setAffineTransform(AffineTransformImpl.createShift(windowSize.getWidth()/2, windowSize.getHeight()/2, 0.));
	}
	
	private void computeScaling(Matrix pos) {
		if (scaling.hasInnerTransform())
			return;
		double distance = pos.normF();
		double wSize = Math.min(windowSize.getHeight(), windowSize.getWidth())/4;
		scaling.setAffineTransform(AffineTransformImpl.createScaling(3, wSize/distance));
	}
	
	public void setWindowDimension(Dimension d) {
		this.windowSize = d;
		scaling.setAffineTransform(null);
		runTransformationUpdate();
	}
	
	public void startAnimation() {
		animationTimer.start();
	}
	
	public void stopAnimation() {
		animationTimer.stop();
	}
	
	private void runTransformationUpdate() {
		if (observedObject == null)
			throw new NullPointerException("Object of your interests is not set. Use option setObservedObject(Object) to set it.");
		if (canDraw() || isEmpty())
			return;
		computeShift();
		Matrix lastPos = pullLast().getBodies().get(observedObject).getPosition();
		computeScaling(lastPos);
		
		if (scaling.hasInnerTransform() && rotation.hasInnerTransform() && shift.hasInnerTransform())
			toPlaneTrans = new BaseToPlaneTransform(transformBuilder.build());
	}
	
	private void updateParticles(Map<Object, Point2D> newPositions) {
		animLock.lock();
		for (Object key : newPositions.keySet()) {
			particles.get(key).setPosition(newPositions.get(key));
		}
		animLock.unlock();
	}
	
	private Collection<? extends SlickDrawable> getDrawableParticles() {
		animLock.lock();
		try {
			return particles.values();
		} finally {
			animLock.unlock();
		}
	}
	
	public void putDrawable(Object id, SlickPositionableDrawable d) {
		animLock.lock();
		particles.put(id, d);
		animLock.unlock();
	}
	
	public void removeDrawable(Object id) {
		animLock.lock();
		particles.remove(id);
		animLock.unlock();
	}

}
