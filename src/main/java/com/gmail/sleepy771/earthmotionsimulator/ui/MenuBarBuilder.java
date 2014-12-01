package com.gmail.sleepy771.earthmotionsimulator.ui;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.gmail.sleepy771.earthmotionsimulator.Builder;

public class MenuBarBuilder implements Builder<JMenuBar> {
	
	// lebo sa mi s tym nechce jebat a vytvarat interface
	public static class Item {		
		private String name;
		private final Set<ActionListener> listeners;
		private final boolean isSepatator;
		
		private Item(String n, Collection<ActionListener> c, boolean isSeparator) {
			name = n;
			this.isSepatator = isSeparator;
			if (isSeparator) {
				listeners = null;
			} else {
				listeners = new HashSet<>();
				if (!(c == null || c.isEmpty()))
					c.addAll(c);
			}
		}
		
		public boolean hasListeners() {
			return !(listeners == null || listeners.isEmpty());
		}
		
		public void addListener(ActionListener listener) {
			if (isSeparator())
				return;
			this.listeners.add(listener);
		}
		
		public void removeListener(ActionListener listener) {
			if (isSeparator())
				return;
			this.listeners.remove(listener);
		}
		
		public String getName() {
			return this.name;
		}
		
		public void setName(String name) {
			this.name = name;
		}
		
		public Set<ActionListener> getListeners() {
			if (listeners == null)
				return new HashSet<>();
			return this.listeners;
		}
		
		public boolean isSeparator() {
			return isSepatator;
		}
		
		// Items are compared by the name, if they have equal name they are equal
		public boolean equals(Object o) {
			if (String.class.isInstance(o)) {
				return ((String) o).equals(getName());
			}
			if (!Item.class.isInstance(o))
				return false;
			return ((Item) o).getName().equals(getName());
		}
		
		public int hashCode() {
			return name.hashCode();
		}
	}
	
	private Map<String, List<Item>> menus;
	private List<String> menuOrder;
	
	public MenuBarBuilder addMenu(String name) {
		return addMenu(-1, name, new ArrayList<Item> ());
	}
	
	public MenuBarBuilder addMenu(int idx, String name, List<Item> items) {
		if (menus.containsKey(name))
			return this;
		if (idx < 0) {
			menuOrder.add(name);
		} else {
			menuOrder.add(idx, name);
		}
		menus.put(name, items);
		return this;
	}
	
	public MenuBarBuilder setMenu(int idx, String name, List<Item> items) {
		if (menus.containsKey(name)) {
			menuOrder.remove(name);
			menuOrder.set(idx, name);
		}
		menus.put(name, items);
		return this;
	}
	
	public MenuBarBuilder removeMenu(String menu) {
		menuOrder.remove(menu);
		menus.remove(menu);
		return this;
	}
	
	public List<Item> getItems(String menu) {
		return menus.get(menu);
	}
	
	public static Item createItem(String name) {
		return new Item(name, null, false);
	}
	
	public static Item createItem(String name, Collection<ActionListener> listeners) {
		return new Item(name, listeners, false);
	}
	
	public static Item createSeparator(String sepId) {
		return new Item(sepId, null, true);
	}
	
	public Item getItem(String menuName, String itemName) {
		List<Item> items = null;
		// Yoda is stronk with this one
		if (null != (items = menus.get(menuName))) {
			for (Item item : items) {
				if (item.getName().equals(itemName))
					return item;
			}
		}
		return null;
	}
	
	public void removeItem(String menuName, String itemName) {
		List<Item> items = null;
		if (null != (items = menus.get(menuName))) {
			items.remove(itemName);
		}
	}
	
	public MenuBarBuilder addItem(int idx, String menuName, Item item) {
		if (!menus.containsKey(menuName))
			addMenu(menuName);
		List<Item> items = menus.get(menuName);
		if (idx < 0) {
			items.add(item);
			return this;
		}
		items.add(idx, item);
		return this;
	}
	
	public MenuBarBuilder addItem(String menuName, Item item) {
		return addItem(menuName, item);
	}
	
	public MenuBarBuilder addItem(String menuName, String itemName, Collection<ActionListener> listeners) {
		return addItem(menuName, createItem(itemName, listeners));
	}
	
	public MenuBarBuilder addItem(String menuName, String itemName, ActionListener listener) {
		return addItem(menuName, createItem(itemName, Arrays.asList(listener)));
	}
	
	public MenuBarBuilder addItem(String menuName, String itemName) {
		return addItem(menuName, createItem(itemName));
	}
	
	public MenuBarBuilder addSeparator(String menuName, String separatorName) {
		return addItem(menuName, createSeparator(separatorName));
	}
	
	@Override
	public JMenuBar build() {
		JMenuBar bar = new JMenuBar();
		for (String menuName : menuOrder) {
			List<Item> items = null;
			// isEmpty is redundant
			if (null != (items = getItems(menuName))) {
				JMenu menu = new JMenu(menuName);
				bar.add(menu);
				for (Item item : items) {
					if (null != item && !"".equals(item.getName())) {
						if (item.isSeparator()) {
							menu.addSeparator();
							continue;
						}
						JMenuItem menuItem = new JMenuItem(item.getName());
						for (ActionListener listener : item.getListeners()) {
							menuItem.addActionListener(listener);
						}
						menu.add(menuItem);
					}
				}
			}
		}
		return bar;
	}

}
