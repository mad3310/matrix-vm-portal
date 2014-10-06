package com.letv.portal.proxy.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.letv.common.session.Session;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.ILoginProxy;
import com.letv.portal.service.IBaseService;

@Component
public class LoginProxyImpl extends BaseProxyImpl<UserLogin> implements ILoginProxy{
	private final static Logger logger = LoggerFactory.getLogger(LoginProxyImpl.class);

	@Override
	public Session saveOrUpdateUserAndLogin(UserLogin userLogin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Session createUserSession(UserModel user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void logout() {
		// TODO Auto-generated method stub
		
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
