package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

import com.gmail.sleepy771.earthmotionsimulator.RunConfiguration;
import com.gmail.sleepy771.earthmotionsimulator.ui.GBCBuilder.GBCFill;

public class SimulationFrame {
	private Canvas canvas;
	private JFrame mainFrame;
	private JMenuBar menuBar;
	private JPanel controlPanel;
	private RunConfiguration config;
	
	private MenuBarBuilder menuBarBuilder;
	
	
	public SimulationFrame(String title, Dimension size, RunConfiguration config) {
		mainFrame = new JFrame(title);
		mainFrame.setLayout(new GridBagLayout());
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.config = config;
	}
	
	public BufferStrategy getStrategy() {
		return mainFrame.getBufferStrategy();
	}
	
	private void initWorkspace() {
		GBCBuilder builder = new GBCBuilder();
		builder.setFill(GBCFill.BOTH).setGridHeight(1).setGridWidth(1).setGridX(0).setGridY(0).setGridYWeight(1.0).setGridXWeight(1.);
		mainFrame.add(getCanvas(), builder.build());
		//mainFrame.add(controlPanel, builder.setGridXWeight(0.2).setGridX(1).build());
	}
	
	private void initDefaultMenu() {
		getMenuBarBuilder().addItem("File", "Open", createOpenListener());
		getMenuBarBuilder().addItem("File", "Save", createSaveListener());
		getMenuBarBuilder().addItem("File", "Save As", createSaveAsListener());
		getMenuBarBuilder().addItem("File", "Close", createCloseListener());
		getMenuBarBuilder().addItem("Edit", "Preferences", createPreferencesListener());
		getMenuBarBuilder().addItem("Data", "Export data", createExportListener());
		getMenuBarBuilder().addItem("Data", "Import data", createImportListener());
		getMenuBarBuilder().addItem("Data", "Delete data");
		getMenuBarBuilder().addItem("Simulation", "Run", createRunListener());
		getMenuBarBuilder().addItem("Simulation", "Stop", createStopListener());
		getMenuBarBuilder().addItem("Simulation", "Free", createFreeSimulationListener());
	}
	
	private ActionListener createOpenListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser openDialog = new JFileChooser();
				int result = openDialog.showOpenDialog(mainFrame);
				openDialog.addChoosableFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						String fileName = f.getName();
						return fileName.endsWith(".xml") || fileName.endsWith(".xml.gz");
					}

					@Override
					public String getDescription() {
						return "XML file";
					}
				});
				openDialog.addChoosableFileFilter(new FileFilter() {
					@Override
					public boolean accept(File f) {
						String fileName = f.getName();
						return fileName.endsWith(".json") || fileName.endsWith(".json.gz");
					}

					@Override
					public String getDescription() {
						return "JSON object file";
					}
				});
				if (JFileChooser.APPROVE_OPTION == result) {
					File loaded = openDialog.getSelectedFile();
					// here comes run open operation with file
				}
			}
		};
	}
	
	private ActionListener createSaveListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createSaveAsListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createCloseListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createPreferencesListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		};
	}
	
	private ActionListener createRunListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createStopListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createFreeSimulationListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createExportListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	private ActionListener createImportListener() {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
		};
	}
	
	public void buildFrame() {
		mainFrame.setJMenuBar(getMenuBarBuilder().build());
		initWorkspace();
	}
	
	public void setVisible(boolean visible) {
		mainFrame.setVisible(visible);
	}
	
	public Canvas getCanvas() {
		if (canvas == null) {
			canvas = new Canvas();
		}
		return canvas;
	}
	
	public void setCanvas(Canvas canvas) {
		mainFrame.remove(this.canvas);
		this.canvas = canvas;
		mainFrame.add(canvas);
	}
	
	private void initActionListeners() {
		
	}
	
	public static void runFrame(final String frameName, final Dimension size) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				//SimulationFrame frame = new SimulationFrame(frameName, size);
			}
		});
	}
	
	public MenuBarBuilder getMenuBarBuilder() {
		if (menuBarBuilder == null) {
			menuBarBuilder = new MenuBarBuilder();
		}
		return menuBarBuilder;
	}
	
	public void setSize(Dimension windwoSize) {
		mainFrame.setSize(windwoSize);
	}
	
	public Dimension getSize() {
		return mainFrame.getSize();
	}
	
	public void showFrame() {
		setVisible(true);
	}
	
	public void hideFrame() {
		setVisible(false);
	}
}
