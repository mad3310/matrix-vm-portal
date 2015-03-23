package com.letv.log.prettyprint;

import java.io.PrintWriter;
import java.io.Writer;

public class PlaintextPrinterFormat implements PrinterFormat {

    private PrintWriter pw;
    private int indent;
    private String tab;

    private boolean isNewline = true;

    public PlaintextPrinterFormat(Writer w) {
        this(w, "   ");
    }

    public PlaintextPrinterFormat(Writer w, String tab) {
        this.pw = w instanceof PrintWriter ? (PrintWriter)w : new PrintWriter(w);
        this.tab = tab;
    }
    
    private String indent() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append(tab);
        }
        return sb.toString();
    }

    private void print(String s) {
        if (isNewline) {
            pw.print(indent());
            isNewline = false;
        }
        
        pw.print(s);
    }
    
    private void newline() {
        if (!isNewline) {
            pw.println();
            isNewline = true;
        }
    }

    @Override
    public void startObject() {
    }

    @Override
    public void endObject() {
        newline();
    }

    @Override
    public void startObjectName() {
    }

    @Override
    public void endObjectName() {
    }

    @Override
    public void startObjectValue() {
        if (!isNewline) {
            print(" ");
        }
        print("{");
        newline(); // must newline before changing indent
        indent++;
    }

    @Override
    public void endObjectValue() {
        newline(); // must newline before changing indent
        indent--;
        print("}");
    }

    @Override
    public void startField() {
    }

    @Override
    public void endField() {
        newline();
    }

    @Override
    public void startFieldName() {
    }

    @Override
    public void endFieldName() {
        if (!isNewline) {
            print(": ");
        }
    }

    @Override
    public void startFieldValue() {
    }

    @Override
    public void endFieldValue() {
    }

    @Override
    public void text(String text) {
        print(text);
    }

	@Override
	public void note(String type, String text) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(type);
		if (text != null) {
			sb.append(": ");
			sb.append(text);
		}
		sb.append("]");
		
		print(sb.toString());
	}
}
