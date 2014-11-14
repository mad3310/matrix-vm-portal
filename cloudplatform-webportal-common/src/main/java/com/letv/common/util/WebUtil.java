package com.letv.common.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

public class WebUtil {
	
	public static String getRequestUrl(HttpServletRequest request) {
    	StringBuffer url = request.getRequestURL();
    	String query = request.getQueryString();
    	if (query != null) {
        	url.append('?');
        	url.append(query);
    	}
    	return url.toString();
    }
    
    public static String getRequestUri(HttpServletRequest request) {
       StringBuffer url = new StringBuffer(request.getRequestURI());
       String query = request.getQueryString();
       if (query != null) {
          url.append('?');
          url.append(query);
       }
       return url.toString();
     }
    
    public static String urlEncode(String s) {
    	try {
			return URLEncoder.encode(s, "UTF-8");
		} 
    	catch (UnsupportedEncodingException e) {
    		throw new RuntimeException(e);
		}
    }
    
    public static String urlDecode(String s) {
    	try {
			return URLDecoder.decode(s, "UTF-8");
		} 
    	catch (UnsupportedEncodingException e) {
    		throw new RuntimeException(e);
		}
    }
}
