package com.letv.portal.interceptor;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CookieUtil;
import com.letv.common.util.IpUtil;
import com.letv.common.util.SessionUtil;
import com.letv.lcp.openstack.service.base.IOpenStackService;
import com.letv.mms.cache.ICacheService;
import com.letv.mms.cache.factory.CacheFactory;
import com.letv.portal.enumeration.LoginClient;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.common.ILoginRecordService;

/**
 * 处理session超时的拦截器
 */
@Component
public class SessionTimeoutInterceptor  implements HandlerInterceptor{
    private final static Logger logger = LoggerFactory.getLogger(SessionTimeoutInterceptor.class);

    @Autowired(required=false)
    private SessionServiceImpl sessionService;
    @Autowired
    private IOpenStackService openStackService;

    @Autowired
    private ILoginProxy loginProxy;

    public String[] allowUrls;
    @Autowired
    private ILoginRecordService loginRecordService;

    @Value("${oauth.token.cache.expire}")
    public long OAUTH_TOKEN_CACHE_EXPIRE;

    private static String OAUTH_CLIENT_ID = "client_id";
    private static String OAUTH_CLIENT_SECRET = "client_secret";
    private ICacheService<?> cacheService = CacheFactory.getCache();

    public void setAllowUrls(String[] allowUrls) {
        this.allowUrls = allowUrls;
    }

    private boolean allowUrl(HttpServletRequest request){
        String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");
        if("/".equals(requestUrl)) {
            return true;
        }
        //特殊url过滤
        if(null != allowUrls && allowUrls.length>=1) {
            for(String url : allowUrls) {
                if(requestUrl.contains(url)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object arg2) throws Exception {
        if(allowUrl(request))
            return true;

        Cookie memoryCookie = CookieUtil.getCookieByName(request, CookieUtil.MEMORY_COOKIE_KEY);
        Cookie diskCookie = CookieUtil.getCookieByName(request, CookieUtil.DISK_COOKIE_KEY);
        Session session = null;
        if (null != memoryCookie && null !=diskCookie) {
            session = this.loginProxy.getUserBySessionId(memoryCookie.getValue());
        }
        if(null == session) {
            //session is null,login by request
            String clientId = request.getParameter(OAUTH_CLIENT_ID);
            String clientSecret = request.getParameter(OAUTH_CLIENT_SECRET);

            session = this.loginProxy.login(clientId,clientSecret);
            if(null !=session)
                this.loginRecordService.insert(session.getUserId(),IpUtil.getIp(request), LoginClient.APP,true);
        }

        //login success,session not null.
        if(null != session)
            return loginSuccess(session, request,response);

        //login failed by request.
        return toLogin(request,response);

    }

    private boolean toLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        CookieUtil.delCookieByDomain(CookieUtil.MEMORY_COOKIE_KEY,response,CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.delCookieByDomain(CookieUtil.DISK_COOKIE_KEY,response,CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.delCookieByDomain(CookieUtil.COOKIE_KEY_USER_ID,response,CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.delCookieByDomain(CookieUtil.COOKIE_KEY_USER_NAME,response,CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.delCookieByDomain(CookieUtil.COOKIE_KEY_HEAD_PORTRAIT,response, CookieUtil.LCP_COOKIE_DOMAIN);
        boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false;
        if (isAjaxRequest) {
            responseJson(request,response,"长时间未操作，请重新登录");
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/toLogin?back="+request.getRequestURI());
            rd.forward(request, response);
        }
        return false;
    }
    private boolean loginSuccess(Session session,HttpServletRequest request,HttpServletResponse response){
        session.setOpenStackSession(openStackService.createSession(session.getUserId(),session.getEmail(),session.getUserName()));
        String cookieStr = SessionUtil.generateSessionId(session.getOauthId(), session.getClientId(), session.getClientSecret());

        CookieUtil.addCookieWithDomain(response,CookieUtil.MEMORY_COOKIE_KEY,cookieStr,CookieUtil.MEMORY_MAX_AGE,CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.addCookieWithDomain(response,CookieUtil.DISK_COOKIE_KEY,cookieStr,CookieUtil.DISK_COOKIE_AGE,CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.addCookieWithDomain(response, CookieUtil.COOKIE_KEY_USER_ID, String.valueOf(session.getUserId()), CookieUtil.MEMORY_MAX_AGE, CookieUtil.LCP_COOKIE_DOMAIN);
        CookieUtil.addCookieWithDomain(response, CookieUtil.COOKIE_KEY_USER_NAME, String.valueOf(session.getUserName()), CookieUtil.MEMORY_MAX_AGE, CookieUtil.LCP_COOKIE_DOMAIN);
        sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
                return null;
            }
        });
        return true;
    }


    @Override
    public void afterCompletion(HttpServletRequest arg0,
                                HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
                           Object arg2, ModelAndView arg3) throws Exception {
    }

    private void responseJson(HttpServletRequest req, HttpServletResponse res, String message) {
        PrintWriter out = null;
        try {
            res.setContentType("text/html;charset=UTF-8");
            out = res.getWriter();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        ResultObject resultObject = new ResultObject(0);
        resultObject.addMsg(message);
        out.append(JSON.toJSONString(resultObject, SerializerFeature.WriteMapNullValue));
        out.flush();
    }

}