package com.letv.log.prettyprint;

import java.io.PrintWriter;
import java.io.Writer;

public class HtmlPrinterFormat implements PrinterFormat {

    private PrintWriter pw;

    public HtmlPrinterFormat(Writer w) {
        this.pw = w instanceof PrintWriter ? (PrintWriter)w : new PrintWriter(w);
    }
        
    @Override
    public void startObject() {
        pw.println("<table border='1' cellspacing='0' cellpadding='5'>");
    }

    @Override
    public void startObjectName() {
        pw.print("<tr valign='top'><th colspan='2' align='left'>");
    }

    @Override
    public void endObjectName() {
        pw.println("</th></tr>");
    }
    
    @Override
    public void startObjectValue() {        
    }
    
    @Override
    public void endObjectValue() {        
    }
    
    @Override
    public void endObject() {
        pw.println("</table>");
    }

    @Override
    public void startField() {
        pw.print("<tr valign='top'>");
    }

    @Override
    public void endField() {
        pw.println("</tr>");
    }

    @Override
    public void startFieldName() {
        pw.print("<td align='left'>");
    }

    @Override
    public void endFieldName() {
        pw.print("</td>");
    }
    
    @Override
    public void startFieldValue() {
        pw.print("<td align='left'>");
    }

    @Override
    public void endFieldValue() {
        pw.print("</td>");
    }

    @Override
    public void text(String text) {
        pw.print(text);
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
		
        pw.print(sb.toString());
	}

}
