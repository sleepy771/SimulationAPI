package com.gmail.sleepy771.earthmotionsimulator.sample;

import java.awt.Dimension;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Ellipse;

import com.gmail.sleepy771.earthmotionsimulator.RunSimulation;
import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickDrawableCollection;
import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickPositionableDrawable;
import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickShapeDrawable;
import com.gmail.sleepy771.earthmotionsimulator.draw.slick.SlickTrackingLine;
import com.gmail.sleepy771.earthmotionsimulator.objects.Body;
import com.gmail.sleepy771.earthmotionsimulator.recordstreaming.BaseRecordSelector;
import com.gmail.sleepy771.earthmotionsimulator.ui.slick.SimulationRenderer;

public class EarthSimSample {
	
	public static void main(String[] args) throws SlickException {
		
		Dimension windowSize = new Dimension(1000, 600);
		
		SimulationRenderer renderer = new SimulationRenderer();
		AppGameContainer container = new AppGameContainer(renderer);
		container.setDisplayMode(windowSize.width, windowSize.height, false);
		
		EarthMotionConfig rc = new EarthMotionConfig();
		RunSimulation sim = new RunSimulation(rc);
		
		SlickSolarSystemAnimation animation = new SlickSolarSystemAnimation(windowSize, rc.getObserved());
		((ParticleSystem) rc.getSystem()).addSink(new BaseRecordSelector<>(animation, new EarthMotionDelayTrigger(3600000 * 24)));
		renderer.setDrawable(animation);
		
		for (Body b : rc.getBodies()) {
			SlickPositionableDrawable draw = null;
			if (b.getId() == rc.getObserved()) {
				draw = new SlickDrawableCollection(new SlickTrackingLine(Color.red, 2f), new SlickShapeDrawable(new Ellipse(0, 0, 10, 10), Color.red, Color.red, 1f));
			} else if (b.getId().toString().equals("Sun")) {
				draw = new SlickDrawableCollection(new SlickTrackingLine(Color.yellow, 2f), new SlickShapeDrawable(new Ellipse(0, 0, 20, 20), Color.yellow, Color.yellow, 1f));
			} else {
				draw = new SlickShapeDrawable(new Ellipse(0,0,5,5));
			}
			animation.putDrawable(b.getId(), draw);
		}
		
		sim.runSimulation();
		animation.startAnimation();
		container.start();
	}
}
