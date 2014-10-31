package com.letv.common.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import net.sf.cglib.proxy.Enhancer;

public final class BeanUtil {
	public final static String PRIMARYKEY = "id";

	public final static String DELETEDSIGN = "_deleted_";
	private static List<String> filterList = Arrays.asList(new String[] { "createdOn", "updatedOn", "updatedBy", "createdBy", PRIMARYKEY });

	public static boolean hasField(Class<?> clazz, String fieldName) {
	       return getField(clazz, fieldName) != null;
	}
	   
	   public static Field getField(Class<?> clazz, String fieldName) {
	       if (clazz == null)
	           return null;
	       clazz = getOriginalClass(clazz);
	       Field temp = null;
	       try {
	           Class<?> clz = clazz;
	           while (clz != null && clz != Object.class) {
	               try {
	                   temp = clz.getDeclaredField(fieldName);
	               }
	               catch (Exception e) {
	               }
	               if (temp != null) {
	                   break;
	               }
	               else {
	                   clz = clz.getSuperclass();
	               }
	           }
	       }
	       catch (SecurityException e) {
	       }
	       return temp;
	   }
	   
	   public static Class<?> getOriginalClass(Class<?> clazz) {
	       Class<?> ret = clazz;
	       while (Enhancer.isEnhanced(ret)) {
	           ret = ret.getSuperclass();
	       }
	       return ret;
	   }
   
}


