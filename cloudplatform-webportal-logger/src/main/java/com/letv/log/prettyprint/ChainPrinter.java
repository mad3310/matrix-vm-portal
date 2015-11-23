package com.letv.log.prettyprint;

public class ChainPrinter implements Printer {

    private Iterable<Printer> printers;
    
    public ChainPrinter(Iterable<Printer> printers) {
        this.printers = printers;
    }
    
    @Override
    public boolean print(Object o, PrinterContext context) {
        for (Printer printer : printers) {
            if (printer.print(o, context)) {
                return true;
            }
        }
        return false;
    }

}
