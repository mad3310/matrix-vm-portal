package com.letv.common.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;


/**
 * Request处理类
 * 
 * @author zxwu
 */
public final class RequestUtil {
    private static final String COMMA = ",";

    private RequestUtil() {
    }


    /**
     * 获得客户端浏览器IP地址
     * 
     * @param req http request
     * @return IP address
     */
    public static String getIpAddress(HttpServletRequest req) {
        String ip = req.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip)) {
            if (ip.contains(COMMA)) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        } else {
            ip = req.getRemoteAddr();
        }
        return ip;
    }
}
