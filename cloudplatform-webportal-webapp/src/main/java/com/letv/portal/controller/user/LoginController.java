package com.letv.portal.controller.user;

import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.IpUtil;
import com.letv.portal.enumeration.LoginClient;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.common.ILoginRecordService;
import com.letv.portal.service.openstack.OpenStackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private OpenStackService openStackService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;

	@Autowired
	private ILoginProxy loginProxy;
    @Autowired
    private ILoginRecordService loginRecordService;

	private static String OAUTH_CLIENT_ID = "client_id";
	private static String OAUTH_CLIENT_SECRET = "client_secret";

	@Value("${oauth.auth.http}")
	private String OAUTH_AUTH_HTTP;
	@Value("${webportal.local.http}")
	private String WEBPORTAL_LOCAL_HTTP;

	@RequestMapping("/oauth/callback")
	public ModelAndView afterlogin(HttpServletRequest request,ModelAndView mav) throws Exception {

		String clientId = request.getParameter(OAUTH_CLIENT_ID);
		String clientSecret = request.getParameter(OAUTH_CLIENT_SECRET);

        Session session = this.loginProxy.login(clientId,clientSecret);

		if(null == session)
			return toLogin(mav,request.getRequestURI(),request.getQueryString());

//        try {
//            session.setOpenStackSession(openStackService.createSession(session.getUserId(),session.getEmail(),session.getEmail(),session.getUserName()));
//        } catch (OpenStackException e) {
//            logger.error("set openstack session error when oauhtLogin:{}",e.getMessage());
//        }
        this.loginRecordService.insert(session.getUserId(),IpUtil.getIp(request), LoginClient.APP,true);
        loginSuccess(request, session);

		/*mav.setViewName("redirect:" + request.getParameter("redirect"));*/
        mav.setViewName("redirect:/profile");
		return mav;
	}

    private void loginSuccess(HttpServletRequest request,Session session) {
        request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
        sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
                return null;
            }
        });
    }

	private ModelAndView toLogin(ModelAndView mav,String requestURI,String queryString) {
		StringBuffer buffer = new StringBuffer();
		/*buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append(requestURI).append(StringUtils.isEmpty(queryString) ? "" : "?&").append(StringUtils.isEmpty(queryString)?"":queryString);*/
        buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append("/oauth/callback");
		mav.setViewName("redirect:" + buffer.toString());
		return mav;
	}
}
