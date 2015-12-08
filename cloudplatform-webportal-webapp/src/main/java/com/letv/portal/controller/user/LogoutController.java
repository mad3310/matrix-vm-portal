package com.letv.portal.controller.user;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letv.common.session.Session;
import com.letv.common.util.CookieUtil;
import com.letv.common.util.HttpsClient;
import com.letv.common.util.SessionUtil;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.proxy.impl.LoginProxyImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.letv.common.session.SessionServiceImpl;

@Controller
@RequestMapping(value="/account")
public class LogoutController{

    public static final String DASHBORAD_ADDRESS = "/profile";

    @Value("${oauth.auth.http}")
    private String OAUTH_AUTH_HTTP;
    @Value("${webportal.local.http}")
    private String WEBPORTAL_LOCAL_HTTP;

    @Autowired
    private ILoginProxy loginProxy;

    @Autowired
    private SessionServiceImpl sessionService;
    private ICacheService<?> cacheService = CacheFactory.getCache();

    private final static String OAUTH_REDIRECT_KEY_SECRET = "&app_key=chenle&app_secret=newpasswd";

    /**Methods Name: logout <br>
     * Description: 用户登出<br>
     * @author name: liuhao1
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value="/logout",method=RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception{

        loginProxy.logout();
        Session session = (Session) request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
        Cookie cookie = CookieUtil.getCookieByName(request, CookieUtil.COOKIE_KEY);
        if(null != cookie) {
            session = (Session) this.cacheService.get(SessionUtil.getUuidBySessionId(cookie.getValue()),null);
            CookieUtil.delCookieByDomain(CookieUtil.COOKIE_KEY,response,CookieUtil.LCP_COOKIE_DOMAIN);
            CookieUtil.delCookieByDomain(CookieUtil.COOKIE_KEY_USER_ID,response,CookieUtil.LCP_COOKIE_DOMAIN);
            CookieUtil.delCookieByDomain(CookieUtil.COOKIE_KEY_USER_NAME,response,CookieUtil.LCP_COOKIE_DOMAIN);
        }
        if(session != null) {
            String clientId = session.getClientId();
            String clientSecret = session.getClientSecret();

            StringBuffer buffer = new StringBuffer();
            buffer.append(OAUTH_AUTH_HTTP).append("/logout?client_id=").append(clientId).append("&client_secret=").append(clientSecret).append(OAUTH_REDIRECT_KEY_SECRET);

            sessionService.setSession(null,"logout");
            HttpsClient.sendXMLDataByGet(buffer.toString(), 1000, 1000);
        }
        StringBuffer buffer = new StringBuffer();
        buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append(DASHBORAD_ADDRESS);
        response.sendRedirect(buffer.toString());

    }
}
