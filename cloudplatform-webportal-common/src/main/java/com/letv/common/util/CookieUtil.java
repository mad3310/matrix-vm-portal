package com.letv.common.util;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**Program Name: CookieUtil <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年1月20日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class CookieUtil {
	private final static Logger logger = LoggerFactory.getLogger(CookieUtil.class);
	
	/**Methods Name: addCookie <br>
	 * Description: 添加cookie<br>
	 * @author name: liuhao1
	 * @param response
	 * @param name
	 * @param value
	 * @param maxAge
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
	    Cookie cookie = new Cookie(name,value);
	    cookie.setPath("/");
	    if(maxAge>0)  cookie.setMaxAge(maxAge);
	    response.addCookie(cookie);
	}
	
	/**Methods Name: getCookieByName <br>
	 * Description: 根据cookie名称获取cookie<br>
	 * @author name: liuhao1
	 * @param request
	 * @param name
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(!cookieMap.containsKey(name))
	    	return null;
        Cookie cookie = (Cookie)cookieMap.get(name);
        return cookie;
	}
	
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
}
