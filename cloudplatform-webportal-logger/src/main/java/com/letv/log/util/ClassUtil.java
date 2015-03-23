package com.letv.log.util;

public class ClassUtil {
	public static String getNameForDisplay(Class<?> c) {
		String name = c.getCanonicalName();
		if (name == null) {
			name = c.getName();
		}
		return name;
	}
}
