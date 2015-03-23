package com.letv.log.prettyprint;

public interface PrinterContext {
    PrinterFormat getPrinterFormat();
    Printer getRootPrinter();
    boolean isRepeat(Object o);
}
