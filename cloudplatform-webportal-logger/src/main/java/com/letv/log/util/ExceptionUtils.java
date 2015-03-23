package com.letv.log.util;

public class ExceptionUtils {

    public static String getDescriptionWithRootCause(Throwable t) {
        Throwable rootCause = org.apache.commons.lang3.exception.ExceptionUtils.getRootCause(t);
        if (rootCause != null && rootCause != t) {
            return t + " (caused by " + rootCause + ")";
        }
        else {
            return t.toString();
        }
    }
}
