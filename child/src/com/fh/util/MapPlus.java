package com.fh.util;

import java.util.HashMap;

public class MapPlus extends HashMap<Object, Object> {
	public MapPlus() {
	}

	public MapPlus(Object firstKey, Object firstValue) {
		this.put(firstKey, firstValue);
	}

	public MapPlus addParams(Object otherKey, Object otherValue) {
		this.put(otherKey, otherValue);
		return this;
	}

}
