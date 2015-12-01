package com.letv.portal.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.letv.common.result.ResultObject;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.oauth.IOauthService;
import com.letv.portal.service.oauth.IUcService;
import com.letv.portal.service.openstack.OpenStackService;
import com.letv.portal.service.openstack.exception.OpenStackException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private IOauthService oauthService;
    @Autowired
    private IUcService ucService;
    @Autowired
    private OpenStackService openStackService;

    @Autowired
    private ILoginProxy loginProxy;

    public String[] allowUrls;

    private static String OAUTH_CLIENT_ID = "client_id";
    private static String OAUTH_CLIENT_SECRET = "client_secret";

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
        if(session != null ) {
            return pass(session,request);
        }
        //session is null,login by request
        session = oauthLogin(request);

        //login success,session not null.
        if(session != null)
            return pass(session,request);


        //login failed by request.
        return toLogin(request,response);

    }

    private Session oauthLogin(HttpServletRequest request) {
        String clientId = request.getParameter(OAUTH_CLIENT_ID);
        String clientSecret = request.getParameter(OAUTH_CLIENT_SECRET);

        if(StringUtils.isEmpty(clientId) || StringUtils.isEmpty(clientSecret))
            return null;
        Map<String,Object> oauthUser = this.oauthService.getUserdetailinfo(clientId, clientSecret);
        if(null == oauthUser || oauthUser.isEmpty())
            return null;

        String oauthId = (String) oauthUser.get("uuid");
        Long ucId = this.ucService.getUcIdByOauthId(oauthId);
        Session session = new Session();
        //use clinetId when user logout.
        session.setClientId(clientId);
        session.setClientSecret(clientSecret);

        session.setOauthId(oauthId);
        session.setUcId(ucId);
        String username = (String) oauthUser.get("username");
        String email = (String) oauthUser.get("email");

        session.setUserName(username);
        session.setEmail(email);
        session.setMobile((String) oauthUser.get("telephone"));

        session = this.loginProxy.saveOrUpdateUserBySession(session);
        try {
            session.setOpenStackSession(openStackService.createSession(session.getUserId(),email,email,username));
        } catch (OpenStackException e) {
            logger.error("set openstack session error when oauhtLogin:{}",e.getMessage());
        }

        return session;
    }

    private boolean toLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
        boolean isAjaxRequest = (request.getHeader("x-requested-with") != null)? true:false;
        if (isAjaxRequest) {
            responseJson(request,response,"长时间未操作，请重新登录");
        } else {
            RequestDispatcher rd = request.getRequestDispatcher("/toLogin?back="+request.getRequestURI());
            rd.forward(request, response);
        }
        return false;
    }
    private boolean pass(Session session,HttpServletRequest request){
        request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
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