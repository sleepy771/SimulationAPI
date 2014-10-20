package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Dimension;
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

import com.gmail.sleepy771.earthmotionsimulator.Receiver;
import com.gmail.sleepy771.earthmotionsimulator.SpaceSimulationRecord;

public class SimulationFrame implements Receiver<SpaceSimulationRecord>{
	private JPanel canvas;
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private static final String SEPARATOR = "a0f0bc95016c862498bbad29d1f4d9d4";
	
	public SimulationFrame(String title, Dimension size) {
		mainFrame = new JFrame(title);
		mainFrame.setJMenuBar(createMenuBar());
		mainFrame.setLayout(new GridBagLayout());
		mainFrame.setSize(size);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public BufferStrategy getStrategy() {
		return mainFrame.getBufferStrategy();
	}
	
	private JPanel createWorkSpace() {
		JPanel workspace = new JPanel();
		JPanel controlPanel = new JPanel();
		return workspace;
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
				frame.setVisible(true);
			}
		});
	}
}
