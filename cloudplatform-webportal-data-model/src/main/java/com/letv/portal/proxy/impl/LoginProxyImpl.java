package com.letv.portal.proxy.impl;

import com.letv.common.exception.ValidateException;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.IpUtil;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class LoginProxyImpl  implements ILoginProxy{

	private final static Logger logger = LoggerFactory.getLogger(LoginProxyImpl.class);

	@Autowired
	private IUserService userService;
	@Autowired
	private SessionServiceImpl sessionService;

	@Override
	public Session saveOrUpdateUserBySession(Session session) {
		if(null == session)
			return null;
		UserModel user = this.userService.selectByOauthId(session.getOauthId());
		Long userId;
		if(null == user) {
			userId = this.insertUser(session);
		} else {
			userId = user.getId();
			user.setEmail(session.getEmail());
			user.setUserName(session.getUserName());
			user.setMobile(session.getMobile());
			this.userService.updateBySelective(user);
		}
		session.setUserId(userId);

		logger.info("logined successfully:{}",session.getUserName());
		return session;
	}
	public Long insertUser(Session session) {
		UserModel userModel = new UserModel();
		userModel.setUcId(session.getUcId());
		userModel.setOauthId(session.getOauthId());
		userModel.setEmail(session.getEmail());
		userModel.setUserName(session.getUserName());
		userModel.setMobile(session.getMobile());
		this.userService.saveUserObject(userModel);
		return userModel.getId();
	}

	@Override
	public Session saveOrUpdateUserAndLogin(UserLogin userLogin) {
		Assert.notNull(userLogin);

		String userNamePassport = userLogin.getLoginName();
		String loginIp = userLogin.getLoginIp();
		String email = userLogin.getEmail();

		if(userNamePassport == null || "".equals(userNamePassport))
			throw new ValidateException("userNamePassort should be not null");

		UserModel user = this.userService.getUserByNameAndEmail(userNamePassport, email);
		if(null == user) {
			user = this.userService.saveUserObjectWithSpecialName(userNamePassport, loginIp, email);
		} else {
			this.userService.updateUserLoginInfo(user, loginIp);
		}
		final Session session = this.createUserSession(user);

		logger.debug("logined successfully");
		return session;
	}

	@Override
	public void logout() {
		logger.debug("logout start");

		sessionService.runWithSession(null, "用户退出", new Executable<Session>(){
			@Override
			public Session execute() throws Throwable {
				return null;
			}
		});

		logger.debug("logouted successfully");
	}

	private Session createUserSession(UserModel user) {
		Session session = new Session();
		session.setUserId(user.getId());
		session.setUserName(user.getUserName());
		session.setEmail(user.getEmail());
		session.setAdmin(user.isAdmin());
		return session;
	}
}
