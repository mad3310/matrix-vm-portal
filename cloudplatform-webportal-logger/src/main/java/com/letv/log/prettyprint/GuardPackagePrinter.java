package com.letv.log.prettyprint;

public class GuardPackagePrinter implements Printer {

    private final String packagePrefix;
    private final Printer printer;

    public GuardPackagePrinter(String packagePrefix, Printer printer) {
        this.packagePrefix = packagePrefix;
        this.printer = printer;
    }
    
    @Override
    public boolean print(Object o, PrinterContext context) {
        if (o.getClass().getPackage().getName().startsWith(packagePrefix)) {
            return printer.print(o, context);
        }
        return false;
    }

}
