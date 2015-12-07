package com.letv.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by liuhao1 on 2015/12/7.
 */
public class CookieUtil {
    public static final String COOKIE_KEY = "matrix_uc_cookie";

    private static final int MEMORY_COOKIE_AGE = -1;
    private static final String UC_COOKIE_DOMAIN = "letvcloud.com";
    private static final String DELETE_COOKIE_VALUE = null;
    public static final int USER_NAME_MAX_AGE = 3600 * 24;
    private static final int DELETE_COOKIE_MAX_AGE = 0;

    public static void addCookieWithDomain(HttpServletResponse response,String name,String value,int maxAge, String domain){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setDomain(domain);
        response.addCookie(cookie);
    }
    public static void delCookieByDomain(String loginCookieName, HttpServletResponse response, String domain) {
        addCookieWithDomain(response, loginCookieName, DELETE_COOKIE_VALUE, DELETE_COOKIE_MAX_AGE, domain);

    }

}
