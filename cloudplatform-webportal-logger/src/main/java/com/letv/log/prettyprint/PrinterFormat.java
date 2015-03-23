package com.letv.log.prettyprint;

public interface PrinterFormat {
    void startObject();
    void endObject();

    void startObjectName();
    void endObjectName();
    
    void startObjectValue();
    void endObjectValue();

    void startField();
    void endField();

    void startFieldName();
    void endFieldName();
    
    void startFieldValue();
    void endFieldValue();
    
    void text(String text);
    void note(String type, String text);
}
