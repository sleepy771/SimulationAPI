package com.gmail.sleepy771.earthmotionsimulator.io;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.gmail.sleepy771.earthmotionsimulator.Builder;
import com.thoughtworks.xstream.XStream;

public class XstreamAliasBuilder implements Builder<XStream>{
	private Map<Class<?>, String> aliases;
	
	public void setAliases(XStreamAliasesConfig conf) {
		aliases = new HashMap<>(conf.getAliases());
	}

	@Override
	public XStream build() {
		XStream xs = new XStream();
		for (Entry<Class<?>, String> entry : aliases.entrySet()) {
			xs.alias(entry.getValue(), entry.getKey());
		}
		return xs;
	}
}
