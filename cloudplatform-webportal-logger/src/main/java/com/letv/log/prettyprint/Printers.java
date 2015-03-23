package com.letv.log.prettyprint;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.letv.log.util.ClassUtil;
import com.letv.log.util.ExceptionUtils;

public class Printers {
    public static final Printer NULL = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
            if (o == null) {
            	context.getRootPrinter().print(new Note(Note.Type.NULL, null), context);
                return true;
            }
            return false;
        }
    };
    
    public static final Printer REPEAT = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
            if (context.isRepeat(o)) {
            	context.getRootPrinter().print(new Note(Note.Type.REPEAT, null), context);
                return true;
            }
            return false;
        }
    };

    public static final Printer NOTE = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
        	Note note = (Note)o;
            context.getPrinterFormat().note(note.getType().toString(), note.getText());
            return true;
        }
    };
    
    public static final Printer BASIC = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
            String string = o.toString();
            if (string != null) {
    			context.getPrinterFormat().text(string);
            }
            else {
            	context.getRootPrinter().print(new Note(Note.Type.INSTANCE, ClassUtil.getNameForDisplay(o.getClass())), context);
            }
            return true;
        }
    };
    
    public static final Printer PRIVATE = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
        	context.getRootPrinter().print(new Note(Note.Type.PRIVATE, o.toString()), context);
            return true;
        }
    };
    
    public static final Printer THROWABLE = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
            context.getPrinterFormat().text(ExceptionUtils.getDescriptionWithRootCause((Throwable)o));
            return true;
        }
    };

    public static final Printer QUOTED_STRING = new Printer() {
        @Override
        public boolean print(Object o, PrinterContext context) {
            context.getPrinterFormat().text('"' + (String)o + '"');
            return true;
        }
    };
    
    public static final Printer CLASS = new Printer() {
        
        @Override
        public boolean print(Object o, PrinterContext context) {
            context.getPrinterFormat().text( ClassUtil.getNameForDisplay((Class<?>)o) );
            return true;
        }
    };
    
    public static final Printer COMPLETE = new ChainPrinter(Arrays.asList(new Printer[] {
        NULL,
        new GuardClassPrinter(Note.class,        NOTE),
        new GuardClassPrinter(String.class,      QUOTED_STRING),
        new GuardClassPrinter(Number.class,      BASIC),
        new GuardClassPrinter(Date.class,        BASIC),
        new GuardClassPrinter(Calendar.class,    BASIC),
        new GuardClassPrinter(Locale.class,      BASIC),
        new GuardClassPrinter(Class.class,       CLASS),
        new GuardClassPrinter(Throwable.class,   THROWABLE),
        REPEAT,
        new CollectionPrinter(),
        new MapPrinter(),
        new ArrayPrinter(),
        new GuardPackagePrinter("java", BASIC),
        new JavaBeanPrinter(),
        PRIVATE // Fallback if nothing else works
    }));
}
