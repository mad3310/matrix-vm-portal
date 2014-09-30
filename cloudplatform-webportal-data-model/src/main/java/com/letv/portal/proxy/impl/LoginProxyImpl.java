package com.letv.portal.proxy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.letv.common.exception.ValidateException;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.dao.IUserLoginDao;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.IUserService;

@Component
public class LoginProxyImpl extends BaseProxyImpl<UserLogin> implements ILoginProxy{
private final static Logger logger = LoggerFactory.getLogger(LoginProxyImpl.class);
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserLoginDao userLoginDao;
	
	public LoginProxyImpl() {
		super(UserLogin.class);
	}


	public Session saveOrUpdateUserAndLogin(UserLogin userLogin) {
		Assert.notNull(userLogin);
		
		String userNamePassport = userLogin.getUserName();
		String loginIp = userLogin.getLoginIp();
		
		if(userNamePassport == null || "".equals(userNamePassport))
			throw new ValidateException("userNamePassort should be not null");
			
		UserModel user = userService.getUserByName(userNamePassport);
		if(null == user)
			user = userService.saveUserObjectWithSpecialName(userNamePassport,loginIp);
		
		if(null == user)
			throw new ValidateException("用户不存在！");
		
		userService.updateUserLoginInfo(user, loginIp);
		UserModel userInsert = userService.getUserByName(userNamePassport);
		final Session session = this.createUserSession(userInsert);
		
		logger.debug("logined successfully");
		return session;
	}

	public Session createUserSession(UserModel user)
	{
		Session session = new Session();
		session.setUserInfoId(user.getId().toString());
		session.setUserName(user.getUserName());
		
		return session;
	}
	

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


	public void checkingUserStatus(String userName) {
	}


	public IBaseDao<UserLogin> getDao() {
		return userLoginDao;
	}
}
