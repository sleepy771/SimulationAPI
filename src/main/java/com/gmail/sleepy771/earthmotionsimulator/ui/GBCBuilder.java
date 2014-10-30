package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.GridBagConstraints;

public class GBCBuilder {
	enum GBCFill {
		HORIZONTAL(GridBagConstraints.HORIZONTAL), VERTICAL(GridBagConstraints.VERTICAL), BOTH(GridBagConstraints.BOTH);
		
		private final int fill;
		
		GBCFill(int fill) {
			this.fill = fill;
		}
		
		public int getFill() {
			return this.fill;
		}
	}
	
	private final GridBagConstraints gbc;
	
	public GBCBuilder() {
		gbc = new GridBagConstraints();
	}
	
	public GBCBuilder setGridX(int gridx) {
		this.gbc.gridx = gridx;
		return this;
	}
	
	public GBCBuilder setGridY(int gridy) {
		this.gbc.gridy = gridy;
		return this;
	}
	
	public GBCBuilder setGridXWeight(double weight) {
		this.gbc.weightx = weight;
		return this;
	}
	
	public GBCBuilder setGridYWeight(double weight) {
		this.gbc.weighty = weight;
		return this;
	}
	
	public GBCBuilder setGridWidth(int w) {
		this.gbc.gridwidth = w;
		return this;
	}
	
	public GBCBuilder setGridHeight(int h) {
		this.gbc.gridheight = h;
		return this;
	}
	
	public GBCBuilder setFill(GBCFill fill) {
		this.gbc.fill = fill.getFill();
		return this;
	}
	
	public GridBagConstraints build() {
		return this.gbc;
	}
}
