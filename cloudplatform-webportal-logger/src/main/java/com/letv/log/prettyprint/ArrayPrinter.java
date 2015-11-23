package com.letv.log.prettyprint;

import java.lang.reflect.Array;

import com.letv.log.util.ClassUtil;

public class ArrayPrinter implements Printer {

    @Override
    public boolean print(Object o, PrinterContext context) {
        if (o == null) {
            return false;
        }
        
        final Class<?> c = o.getClass();
        
        if (!c.isArray()) {
            return false;
        }

        final int length = Array.getLength(o);

        PrinterFormat format = context.getPrinterFormat();
        
        format.startObject();

        format.startObjectName();
        format.text(ClassUtil.getNameForDisplay(c));
        format.endObjectName();

        format.startObjectValue();
        
        for (int i = 0; i < length; i++) {
            Object element = Array.get(o, i);
            format.startField();

            format.startFieldName();
            format.text(String.valueOf(i));
            format.endFieldName();
            
            format.startFieldValue();
            context.getRootPrinter().print(element, context);
            format.endFieldValue();
            
            format.endField();
        }
        
        format.endObjectValue();
        format.endObject();
        
        return true;
    }

}
