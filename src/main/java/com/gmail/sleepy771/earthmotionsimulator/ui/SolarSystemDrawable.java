package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Dimension2D;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import Jama.Matrix;

import com.gmail.sleepy771.earthmotionsimulator.Body;
import com.gmail.sleepy771.earthmotionsimulator.DataServer;
import com.gmail.sleepy771.earthmotionsimulator.SpaceSimulationRecord;

public class SolarSystemDrawable implements Drawable {
	
	private final DataServer<Void, SpaceSimulationRecord> server;
	private TransformationToSurface transformation;
	private DrawableString legend;
	private Matrix initEarthPos;
	private BlockingQueue<SpaceSimulationRecord> recordBuffer;
	private final Runnable serverReader;
	private boolean hasNormal;
	private DrawableString calculatigNormalText;
	private Map<Object, DrawableShape> drawableBodies;
	private Object earthObject;
//	private AffineTransform affineTransformation;
	private Thread readerThread;
	
	public SolarSystemDrawable(Object observableObject, int bufferSize, DataServer<Void, SpaceSimulationRecord> server, TransformationToSurface trans) {
		this.server = server;
		transformation = trans;
		recordBuffer = new LinkedBlockingQueue<>(bufferSize);
		this.earthObject = observableObject;
		serverReader = new Runnable() {
			@Override
			public void run() {
				try {
					while(SolarSystemDrawable.this.server.isListening()) {
						SpaceSimulationRecord newRecord = SolarSystemDrawable.this.server.get(null);
						if (!hasNormal) {
							if (initEarthPos == null) {
								initEarthPos = newRecord.getBodies().get(earthObject).getPosition();
							} else {
								Matrix rec = null;
								if ((rec = newRecord.getBodies().get(earthObject).getPosition()).minus(initEarthPos).normF() >= initEarthPos.normF() * 0.5) {
									estimateNormal(rec, initEarthPos);
								}
							}
						}
						recordBuffer.put(newRecord);
					}
				} catch (InterruptedException ie) {
					ie.printStackTrace();
				} 
			}
		};
	}
	
	private void estimateNormal(Matrix v, Matrix u) {
		Matrix norm = new Matrix(3,1);
		norm.set(0, 0, v.get(1, 0) * u.get(2, 0) - v.get(2, 0) * u.get(1, 0));
		norm.set(1, 0, v.get(2, 0) * u.get(0, 0) - v.get(0, 0) * u.get(2, 0));
		norm.set(2, 0, v.get(0, 0) * u.get(1, 0) - v.get(1, 0) * u.get(0, 0));
		norm.times(1./norm.normF());
		hasNormal = true;
		transformation.setSurfaceNormal(norm);
	}

	@Override
	public void draw(Graphics2D g2d) {
		if (readerThread == null) {
			readerThread = new Thread(serverReader);
			readerThread.start();
		}
		if ( !hasNormal ) {
			calculatigNormalText.draw(g2d);
			return;
		}
		try {
			SpaceSimulationRecord record = recordBuffer.take();
			// TODO sem napisat kod na vykreslovanie trajektorie
			for (Entry<Object, Body> entry : record.getBodies().entrySet()) {
				DrawableShape db = drawableBodies.get(entry.getKey());
				db.setPosition(transformation.transformToSurface(entry.getValue().getPosition()));
				db.draw(g2d);
			}
			legend.setText("Current time: " + new Double(Math.round(record.getTime())));
			legend.draw(g2d);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
//
//	@Override
//	public void setAffineTransform(AffineTransform af) {
//		this.affineTransformation = af;
//		for (Entry<Object, DrawableShape> d : this.drawableBodies.entrySet()) {
//			d.getValue().setAffineTransform(affineTransformation);
//		}
//	}
//
//	@Override
//	public AffineTransform getAffineTransform() {
//		return this.affineTransformation;
//	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Dimension2D getSize() {
		// TODO Auto-generated method stub
		return null;
	}

}
