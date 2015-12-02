package com.letv.portal.controller.user;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private IOauthService oauthService;
	@Autowired
	private IUcService ucService;
	@Autowired
	private OpenStackService openStackService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;

	@Autowired
	private ILoginProxy loginProxy;

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

		Map<String,Object> oauthUser = this.oauthService.getUserdetailinfo(clientId, clientSecret);
		if(null == oauthUser || oauthUser.isEmpty()) {
			return toLogin(mav,request.getRequestURI(),request.getQueryString());
		}

		Session session = loginSuccess(oauthUser, clientId, clientSecret);
		if(null == session)
			return toLogin(mav,request.getRequestURI(),request.getQueryString());

		request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
		sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
			@Override
			public Session execute() throws Throwable {
				return null;
			}
		});

		/*mav.setViewName("redirect:" + request.getParameter("redirect"));*/
        mav.setViewName("redirect:/profile");
		return mav;
	}

	private ModelAndView toLogin(ModelAndView mav,String requestURI,String queryString) {
		StringBuffer buffer = new StringBuffer();
		/*buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append(requestURI).append(StringUtils.isEmpty(queryString) ? "" : "?&").append(StringUtils.isEmpty(queryString)?"":queryString);*/
        buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_LOCAL_HTTP).append("/oauth/callback");
		mav.setViewName("redirect:" + buffer.toString());
		return mav;
	}
	private Session loginSuccess(Map<String,Object> oauthUser,String clientId,String clientSecret) {

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
			return null;
		}
		return session;
	}
}
