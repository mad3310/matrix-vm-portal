package com.letv.log.prettyprint;

import java.util.Collection;

import com.letv.log.util.ClassUtil;

public class CollectionPrinter implements Printer {

    @Override
    public boolean print(Object o, PrinterContext context) {
        if (!(o instanceof Collection<?>)) {
            return false;
        }
        
        Collection<?> c = (Collection<?>)o;
              
        PrinterFormat format = context.getPrinterFormat();
        
        format.startObject();
        format.startObjectName();
        format.text(ClassUtil.getNameForDisplay(o.getClass()));
        format.endObjectName();
        
        format.startObjectValue();

        for (Object element : c) {
            format.startField();

            format.startFieldName();
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
