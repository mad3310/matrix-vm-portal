package com.letv.log.prettyprint;

import java.util.Map;
import java.util.Map.Entry;

import com.letv.log.util.ClassUtil;

public class MapPrinter implements Printer {

    @Override
    public boolean print(Object o, PrinterContext context) {
        if (!(o instanceof Map<?,?>)) {
            return false;
        }
        
        Map<?,?> map = (Map<?,?>)o;
        
        PrinterFormat format = context.getPrinterFormat();
        
        format.startObject();
        format.startObjectName();
        format.text(ClassUtil.getNameForDisplay(o.getClass()));
        format.endObjectName();
        
        format.startObjectValue();

        for (Entry<?,?> entry : map.entrySet()) {
            format.startField();

            format.startFieldName();
            context.getRootPrinter().print(entry.getKey(), context);
            format.endFieldName();
            
            format.startFieldValue();
            context.getRootPrinter().print(entry.getValue(), context);
            format.endFieldValue();
            
            format.endField();
        }

        format.endObjectValue();
        format.endObject();
        
        return true;
    }

}
