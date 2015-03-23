package com.letv.log.prettyprint;

import java.io.PrintWriter;
import java.io.Writer;

public class OneLinePrinterFormat implements PrinterFormat {

    private PrintWriter pw;

    public OneLinePrinterFormat(Writer w) {
        this.pw = w instanceof PrintWriter ? (PrintWriter)w : new PrintWriter(w);
    }
    
    private void print(String s) {
        pw.print(s);
    }
    
    @Override
    public void startObject() {
    	print("(");
    }

    @Override
    public void endObject() {
        print(")");
    }

    @Override
    public void startObjectName() {
    }

    @Override
    public void endObjectName() {
    	print(": ");
    }

    @Override
    public void startObjectValue() {
    }

    @Override
    public void endObjectValue() {
    }

    private boolean afterField = false;
    
    @Override
    public void startField() {
    	if (afterField) {
    		afterField = false;
    		print(", ");
    	}
    }

    @Override
    public void endField() {
    	afterField = true;
    }

    @Override
    public void startFieldName() {
    }

    @Override
    public void endFieldName() {
    }

    @Override
    public void startFieldValue() {
        print("=");
    }

    @Override
    public void endFieldValue() {
    }

    private static String stripWhitespace(String s) {
    	StringBuilder sb = new StringBuilder();
    	for (char c : s.toCharArray()) {
    		switch (c) {
    		case '\n':
    		case '\r':
    		case '\t':
    			// replace all these with a single space
    			sb.append(' ');
    			break;
    		default:
    			sb.append(c);
    			break;
    		}
    	}
    	return sb.toString();
    }
    
    @Override
    public void text(String text) {
        print(stripWhitespace(text.toString()));
    }

	@Override
	public void note(String type, String text) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(type);
		if (text != null) {
			sb.append(": ");
			sb.append(stripWhitespace(text));
		}
		sb.append("]");
		
		print(sb.toString());
	}
}
