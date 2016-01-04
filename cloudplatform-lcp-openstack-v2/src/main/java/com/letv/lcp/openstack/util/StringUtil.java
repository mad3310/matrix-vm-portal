package com.letv.lcp.openstack.util;

import org.apache.commons.codec.Charsets;

/**
 * Created by zhouxianguang on 2015/11/19.
 */
public class StringUtil {
    public static String getPrefix(String source, int byteLength) {
        byte[] bytes = source.getBytes(Charsets.UTF_8);
        if (bytes.length > byteLength) {
            return source.substring(0, byteLength / 2);
        }
        return source;
    }
}
