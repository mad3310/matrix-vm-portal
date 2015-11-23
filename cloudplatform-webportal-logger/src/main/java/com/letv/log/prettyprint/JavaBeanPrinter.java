package com.letv.log.prettyprint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.letv.log.util.ClassUtil;

public class JavaBeanPrinter implements Printer {

    private static interface Getter {
        Object get(Object bean);
    }
        
    private static class MethodGetter implements Getter {

        private final Method method;
        
        public MethodGetter(Method method) {
            this.method = method;
        }
        
        @Override
        public Object get(Object bean) {
            try {
                return this.method.invoke(bean);
            }
            catch (Throwable t) {
            	Throwable rootCause = ExceptionUtils.getRootCause(t);
            	if (rootCause == null) {
            		rootCause = t;
            	}
                return new Note(Note.Type.UNAVAILABLE, rootCause.toString());
            }
        }
        
        public String toString() {
        	return method.toString();
        }
    }
    
    private static class FieldGetter implements Getter {

        private final Field field;
        
        public FieldGetter(Field field) {
            this.field = field;
        }
        
        @Override
        public Object get(Object bean) {
            try {
                return field.get(bean);
            }
            catch (Throwable t) {
            	Throwable rootCause = ExceptionUtils.getRootCause(t);
            	if (rootCause == null) {
            		rootCause = t;
            	}
                return new Note(Note.Type.UNAVAILABLE, rootCause.toString());
            }
        }
        
        public String toString() {
        	return field.toString();
        }
    }
    
    private static class ConstantGetter implements Getter {
    	private final Object constant;
    	
    	public ConstantGetter(Object constant) {
    		this.constant = constant;
    	}
    	
    	@Override
    	public Object get(Object bean) {
    		return constant;
    	}
    	
    	public String toString() {
    		return constant.toString();
    	}
    }
    
    @Override
    public boolean print(Object o, PrinterContext context) {
        if (o == null) {
            return false;
        }
        
        // find all the public fields and getters with no args and a non-void return
        Class<?> c = o.getClass();
        
        if (!Modifier.isPublic(c.getModifiers())) {
            return false;
        }
        
        Map<String, Getter> gettersByPropertyName = new TreeMap<String, Getter>();
        
        for (Field field : c.getFields()) {
            if (Modifier.isPublic(field.getModifiers()) && 
                !Modifier.isStatic(field.getModifiers())) 
            {
                gettersByPropertyName.put(field.getName(), new FieldGetter(field));
            }
        }
        
        for (Method method : c.getMethods()) {
            String methodName = method.getName();
            if (methodName.startsWith("get") && 
                Modifier.isPublic(method.getModifiers()) && 
                !Modifier.isStatic(method.getModifiers()) &&
                method.getParameterTypes().length == 0 &&
                !method.getReturnType().equals(Void.TYPE) &&
                !methodName.equals("getClass") &&
                methodName.length() > 3) 
            {
                String propertyName = Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                if (gettersByPropertyName.put(propertyName, new MethodGetter(method)) != null) {
                	gettersByPropertyName.put(propertyName, new ConstantGetter(new Note(Note.Type.UNAVAILABLE, "field '" + propertyName + "' and method '" + methodName + "' both found")));
                }
            }
        }
        
        PrinterFormat format = context.getPrinterFormat();
                
        format.startObject();
        format.startObjectName();
        format.text(ClassUtil.getNameForDisplay(c));
        format.endObjectName();
        
        format.startObjectValue();

        for (Entry<String, Getter> property : gettersByPropertyName.entrySet()) {
            format.startField();

            final String name = property.getKey();
            final Object value = property.getValue().get(o);
            
            format.startFieldName();
            format.text(name);
            format.endFieldName();
            
            format.startFieldValue();
            context.getRootPrinter().print(value, context);
            format.endFieldValue();
            
            format.endField();
        }

        format.endObjectValue();
        format.endObject();
        
        return true;
        
    }

}
