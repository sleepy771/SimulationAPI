package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.gmail.sleepy771.earthmotionsimulator.ParalelSimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.Planet;
import com.gmail.sleepy771.earthmotionsimulator.Receiver;
import com.gmail.sleepy771.earthmotionsimulator.SimulationExecutor;
import com.gmail.sleepy771.earthmotionsimulator.SimulationSystem;
import com.gmail.sleepy771.earthmotionsimulator.SolarSystem;
import com.gmail.sleepy771.earthmotionsimulator.SpaceSimulationRecord;
import com.gmail.sleepy771.earthmotionsimulator.ui.GBCBuilder.GBCFill;

public class SimulationFrame implements Receiver<SpaceSimulationRecord>{
	private JPanel canvas;
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JPanel controlPanel;
	private static final String SEPARATOR = "a0f0bc95016c862498bbad29d1f4d9d4";
	
	public SimulationFrame(String title, Dimension size) {
		mainFrame = new JFrame(title);
		mainFrame.setJMenuBar(createMenuBar());
		mainFrame.setLayout(new GridBagLayout());
		mainFrame.setSize(size);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initWorkSpace();
	}
	
	public BufferStrategy getStrategy() {
		return mainFrame.getBufferStrategy();
	}
	
	private void initWorkSpace() {
		canvas = new JPanel();
		controlPanel = new JPanel();
		GBCBuilder builder = new GBCBuilder();
		builder.setFill(GBCFill.BOTH).setGridHeight(1).setGridWidth(1).setGridX(0).setGridY(0).setGridYWeight(1.0).setGridXWeight(0.8);
		mainFrame.add(canvas, builder.build());
		mainFrame.add(controlPanel, builder.setGridXWeight(0.2).setGridX(1).build());
	}
	
	public void setVisible(boolean visible) {
		mainFrame.setVisible(visible);
	}
	
	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
//		JMenu file = createMenu("File");
		return menuBar;
	}
	
	private JMenu createMenu(String name, List<JMenuItem> items) {
		JMenu menu = new JMenu(name);
		for (JMenuItem item : items) {
			if (item.getName() == SEPARATOR)
				menu.addSeparator();
			else
				menu.add(item);
		}
		return menu;
	}
	
	private JMenuItem createMenuItem(String name, ActionListener clickListener) {
		JMenuItem item = new JMenuItem(name);
		item.addActionListener(clickListener);
		return item;
	}
	
	private JMenuItem createSeparatorMenuItem() {
		return new JMenuItem(SEPARATOR);
	}
	
	public JPanel getCanvas() {
		return this.canvas;
	}
	
	private void initActionListeners() {
	}

	@Override
	public void onReceive(SpaceSimulationRecord rec) {
		// TODO Auto-generated method stub
		
	}
	
	public static void runFrame(final String frameName, final Dimension size) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SimulationFrame frame = new SimulationFrame(frameName, size);
				SolarSystem sys;
//				SimulationExecutor executor = new ParalelSimulationExecutor(sys);
//				SimulationSystem<Planet> system = new SolarSystem(0, 0.1, bodies, simExec, server, endCondition);
				frame.setVisible(true);
			}
		});
	}
}
