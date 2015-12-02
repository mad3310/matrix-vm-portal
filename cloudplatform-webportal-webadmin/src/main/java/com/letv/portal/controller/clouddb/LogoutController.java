package com.letv.portal.controller.clouddb;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.letv.portal.proxy.ILoginProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.HttpsClient;
import com.letv.portal.service.adminoplog.ClassAoLog;

@ClassAoLog(ignore=true)
@Controller
@RequestMapping(value="/account")
public class LogoutController{

	public static final String DASHBORAD_ADDRESS = "/dashboard";

	@Value("${oauth.auth.http}")
	private String OAUTH_AUTH_HTTP;
	@Value("${webportal.admin.http}")
	private String WEBPORTAL_ADMIN_HTTP;

	@Autowired
	private ILoginProxy loginProxy;

	@Autowired
	private SessionServiceImpl sessionService;

	/**Methods Name: logout <br>
	 * Description: 用户登出<br>
	 * @author name: liuhao1
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/logout",method=RequestMethod.GET)   //http://localhost:8080/account/logout
	public void logout(HttpServletRequest request, HttpServletResponse response) throws Exception{

		loginProxy.logout();
		Session session = (Session) request.getSession().getAttribute(Session.USER_SESSION_REQUEST_ATTRIBUTE);
		if(session != null) {
			String clientId = session.getClientId();
			String clientSecret = session.getClientSecret();

			StringBuffer buffer = new StringBuffer();
			buffer.append(OAUTH_AUTH_HTTP).append("/logout?client_id=").append(clientId).append("&client_secret=").append(clientSecret);

			request.getSession().invalidate();
			sessionService.setSession(null,"logout");
			HttpsClient.sendXMLDataByGet(buffer.toString(),1000,1000);
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(OAUTH_AUTH_HTTP).append("/index?redirect_uri=").append(WEBPORTAL_ADMIN_HTTP).append("/oauth/callback");
		response.sendRedirect(buffer.toString());
	}
}
