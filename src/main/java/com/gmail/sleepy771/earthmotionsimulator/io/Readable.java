package com.gmail.sleepy771.earthmotionsimulator.io;

import java.io.InputStream;

public interface Readable {
	void setInputStream(InputStream stream);
	
	void read();
}
