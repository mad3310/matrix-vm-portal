package com.letv.log.prettyprint;

import java.io.StringWriter;

import org.apache.log4j.Logger;

public class PrettyPrinter {
    
    private static final Logger logger = Logger.getLogger(PrettyPrinter.class);

    private Printer rootPrinter;
    
    public PrettyPrinter() {
        this(Printers.COMPLETE);
    }
    
    public PrettyPrinter(Printer rootPrinter) {
        this.rootPrinter = rootPrinter;
    }
    
    private String handleThrowable(Object o, Throwable t) {
        String fakePrint = String.valueOf(o) + " [exception pretty printing: " + t + "]";
        logger.error(fakePrint, t);
        return fakePrint;
    }
    
    /**
     * Pretty print the given object using the given PrinterFormat.
     * This method CAN throw RuntimeException.
     */
    public void print(Object o, final PrinterFormat format) {
        PrinterContext context = new PrinterContextImpl(format, rootPrinter);
        
        if (!rootPrinter.print(o, context)) {
            throw new RuntimeException("Unable to pretty print " + o);
        }
    }
    
    /**
     * Pretty print the given object using an HTML PrinterFormat and return a string containing the result.
     * This method is guaranteed to NOT THROW anything.
     */
    public String html(Object o) {
        try {
            StringWriter w = new StringWriter();
            HtmlPrinterFormat format = new HtmlPrinterFormat(w);
            print(o, format);
            return w.toString();
        }
        catch (Throwable t) {
            return handleThrowable(o, t);
        }
    }
    
    /**
     * Pretty print the given object using a plaintext PrinterFormat and return a string containing the result.
     * This method is guaranteed to NOT THROW anything.
     */
    public String plaintext(Object o) {
        try {
            StringWriter w = new StringWriter();
            PlaintextPrinterFormat format = new PlaintextPrinterFormat(w);
            print(o, format);
            return w.toString();
        }
        catch (Throwable t) {
            return handleThrowable(o, t);
        }
    }
    
    /**
     * Pretty print the given object using a very terse, one line format and return a string containing the result.
     * This method is guaranteed to NOT THROW anything.
     */
    public String oneLine(Object o) {
        try {
            StringWriter w = new StringWriter();
            OneLinePrinterFormat format = new OneLinePrinterFormat(w);
            print(o, format);
            return w.toString();
        }
        catch (Throwable t) {
            return handleThrowable(o, t);
        }
    }
}
