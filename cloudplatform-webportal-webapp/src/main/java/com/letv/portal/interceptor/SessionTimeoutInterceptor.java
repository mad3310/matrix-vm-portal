package com.letv.portal.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.oauth.IOauthService;
import com.letv.portal.service.oauth.IUcService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * 处理session超时的拦截器
 */
@Component
public class SessionTimeoutInterceptor  implements HandlerInterceptor{
    private final static Logger logger = LoggerFactory.getLogger(SessionTimeoutInterceptor.class);

    @Autowired(required=false)
    private SessionServiceImpl sessionService;

    public String[] allowUrls;

    @Value("${oauth.auth.http}")
    private String OAUTH_AUTH_HTTP;
    @Value("${webportal.local.http}")
    private String WEBPORTAL_LOCAL_HTTP;


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

        Session session = (Session) request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
        if(session != null )
            return pass(session);

        return toLogin(request,response);

    }

    private boolean toLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false;
        if (isAjaxRequest) {
            responseJson(request,response,"长时间未操作，请重新登录");
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append("/oauth/callback?&redirect=").append(request.getRequestURI())
                    .append(StringUtils.isEmpty(request.getQueryString())?"":"?&").append(StringUtils.isEmpty(request.getQueryString())?"":request.getQueryString());
            response.sendRedirect(buffer.toString());
        }
        return false;
    }
    private boolean pass(Session session){
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