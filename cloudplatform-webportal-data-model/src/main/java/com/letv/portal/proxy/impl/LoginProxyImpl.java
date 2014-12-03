package com.letv.portal.proxy.impl;

import org.springframework.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.common.exception.ValidateException;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.IUserLoginDao;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.IBaseService;
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
	@Override
	public Session saveOrUpdateUserAndLogin(UserLogin userLogin) {
		Assert.notNull(userLogin);
		
		String userNamePassport = userLogin.getLoginName();
		String loginIp = userLogin.getLoginIp();
		
		if(userNamePassport == null || "".equals(userNamePassport))
			throw new ValidateException("userNamePassort should be not null");
			
		UserModel user = userService.getUserByName(userNamePassport);
		if(null == user) {
			user = userService.saveUserObjectWithSpecialName(userNamePassport,loginIp);
		} else {
			userService.updateUserLoginInfo(user, loginIp);
		}
		final Session session = this.createUserSession(user);
		
		logger.debug("logined successfully");
		return session;
	}

	@Override
	public Session createUserSession(UserModel user) {
		Session session = new Session();
		session.setUserInfoId(user.getId());
		session.setUserName(user.getUserName());
		
		return session;
	}

	@Override
	public void logout() {
		logger.debug("logout start");
		
		sessionService.runWithSession(null, "session change", new Executable<Session>(){
            @Override
            public Session execute() throws Throwable {
               return null;
            }
         });

		logger.debug("logouted successfully");
		
	}

	@Override
	public void checkingUserStatus(String userName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public com.letv.common.dao.IBaseDao<UserLogin> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IBaseService<UserLogin> getService() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
