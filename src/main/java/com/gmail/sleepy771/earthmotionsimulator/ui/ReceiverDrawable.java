package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.gmail.sleepy771.earthmotionsimulator.Body;
import com.gmail.sleepy771.earthmotionsimulator.DataServer;
import com.gmail.sleepy771.earthmotionsimulator.SpaceSimulationRecord;

public class ReceiverDrawable implements Drawable {

	private AffineTransform affineTrans;
	private final DataServer<Void, SpaceSimulationRecord> server;
	private final Map<Object, Drawable> drawables;
	private final Collection<Drawable> drawableAccessList;
	private SpaceSimulationRecord lastRecord;
	private TransformationToSurface trans;
	private final DrawableString legend;
	
	public ReceiverDrawable(DrawableString legend, DataServer<Void, SpaceSimulationRecord> server, AffineTransform af, Map<Object, Drawable> drawables, TransformationToSurface surfTrans) {
		this.server = server;
		this.affineTrans = af;
		this.drawables = Collections.unmodifiableMap(drawables);
		this.drawableAccessList = Collections.unmodifiableCollection(drawables.values());
		this.trans = surfTrans;
		this.legend = legend;
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		try {
			invokeReceiver();
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}
		for (Drawable d : this.drawableAccessList) {
			d.draw(g2d);
		}
		legend.draw(g2d);
	}

	@Override
	public void setPosition(Point2D p) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAffineTransform(AffineTransform af) {
		this.affineTrans = af;
	}

	@Override
	public AffineTransform getAffineTransform() {
		return affineTrans;
	}

	@Override
	public Point2D getPosition() {
		throw new UnsupportedOperationException();
	}
	
	private void invokeReceiver() throws InterruptedException {
		this.lastRecord = this.server.get(null);
		List<Body> bodies = lastRecord.getBodies();
		double time = lastRecord.getTime();
		legend.setText("Current time: " + new Date(Math.round(time)).toString());
		for (Body body : bodies) {
			Drawable d = this.drawables.get(body.getId());
			d.setPosition(trans.transformToSurface(body.getPosition()));
		}
	}

}
