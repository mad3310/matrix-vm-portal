package com.letv.log.prettyprint;

import java.util.IdentityHashMap;
import java.util.Map;

public class PrinterContextImpl implements PrinterContext {

    private PrinterFormat printerFormat;
    private Printer rootPrinter;

    // have to use a map because there is no IdentityHashSet provided
    private Map<Object, Object> printedAlready = new IdentityHashMap<Object, Object>();
    
    public PrinterContextImpl(PrinterFormat printerFormat, final Printer rootPrinter) {
        this.printerFormat = printerFormat;
        this.rootPrinter = rootPrinter;
    }
    
    @Override
    public PrinterFormat getPrinterFormat() {
        return printerFormat;
    }

    @Override
    public Printer getRootPrinter() {
        return rootPrinter;
    }

    @Override
    public boolean isRepeat(Object o) {
        return printedAlready.put(o, o) != null;
    }

}
