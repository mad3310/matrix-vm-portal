package com.letv.log.prettyprint;

public class GuardClassPrinter implements Printer {

    private final Class<?> guardClass;
    private final Printer printer;

    public GuardClassPrinter(Class<?> guardClass, Printer printer) {
        this.guardClass = guardClass;
        this.printer = printer;
    }
    
    @Override
    public boolean print(Object o, PrinterContext context) {
        if (guardClass.isInstance(o)) {
            return printer.print(o, context);
        }
        return false;
    }

}
