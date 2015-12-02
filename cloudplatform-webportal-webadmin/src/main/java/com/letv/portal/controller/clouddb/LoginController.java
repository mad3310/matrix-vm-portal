package com.letv.portal.controller.clouddb;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letv.portal.proxy.ILoginProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.IpUtil;
import com.letv.portal.model.UserLogin;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.service.oauth.IOauthService;
import com.mysql.jdbc.StringUtils;

@ClassAoLog(ignore=true)
@Controller
public class LoginController{

	private static String WEB_URL = "http://www.letv.com";

	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);
	private final static int OAUTH_API_RETRY_COUNT = 3;

	@Autowired
	private SessionServiceImpl sessionService;
	@Autowired
	private IOauthService oauthService;

	@Value("${oauth.auth.http}")
	private String OAUTH_AUTH_HTTP;
	@Value("${webportal.admin.http}")
	private String WEBPORTAL_ADMIN_HTTP;
	@Autowired
	private ILoginProxy loginProxy;

	/**Methods Name: login <br>
	 * Description: cas登录完成后，跳转到本页面做相关用户记录，然后跳转到业务界面<br>
	 * @author name: liuhao1
	 * @param request
	 * @param mav
	 * @return
	 */
	@RequestMapping("/oauth/callback")
	public ModelAndView afterlogin(HttpServletRequest request,HttpServletResponse response,ModelAndView mav) throws Exception {

		String clientId = request.getParameter("client_id");
		String clientSecret = request.getParameter("client_secret");
		if(StringUtils.isNullOrEmpty(clientId)) {
			StringBuffer buffer = new StringBuffer();
			buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_ADMIN_HTTP).append("/oauth/callback");
			mav.setViewName("redirect:" + buffer.toString());
			return mav;
		}
		Map<String,Object> userDetailInfo = this.oauthService.getUserdetailinfo(clientId, clientSecret);
		String username = (String) userDetailInfo.get("username");
		String email = (String) userDetailInfo.get("email");

		UserLogin userLogin = new UserLogin();
		userLogin.setLoginName(username);
		userLogin.setLoginIp(IpUtil.getIp(request));
		userLogin.setEmail(email);
		Session session = this.loginProxy.saveOrUpdateUserAndLogin(userLogin);
		session.setClientId(clientId);
		session.setClientSecret(clientSecret);
		if(!session.isAdmin()) {
			mav.setViewName("redirect:/403");
			return mav;
		}
		request.getSession().setAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE, session);
		sessionService.runWithSession(session, "Usersession changed", new Executable<Session>(){
			@Override
			public Session execute() throws Throwable {
				return null;
			}
		});

		mav.setViewName("redirect:/dashboard");
		return mav;
	}

}
