package com.gmail.sleepy771.earthmotionsimulator.units;

import com.gmail.sleepy771.earthmotionsimulator.objects.SystemUnit;

public interface UnitConverter<T extends SystemUnit> {
	T convertSystemUnit(T object);
}
