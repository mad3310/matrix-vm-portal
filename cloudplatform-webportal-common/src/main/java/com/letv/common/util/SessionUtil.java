package com.letv.common.util;

import org.apache.commons.lang.StringUtils;

/**
 * Created by liuhao1 on 2015/12/7.
 */
public class SessionUtil {
    private static final String BARRIER = "|";
    public static String generateSessionId(String uuid) {
        if(StringUtils.isEmpty(uuid)){
            return "";
        }
        return new StringBuilder(uuid).append(BARRIER).append(System.nanoTime()).toString();
    }

}
