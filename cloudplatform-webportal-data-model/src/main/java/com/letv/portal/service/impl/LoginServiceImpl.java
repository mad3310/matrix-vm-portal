package com.letv.portal.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.Executable;
import com.letv.common.session.Session;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.IUserLoginDao;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.service.ILoginService;
import com.letv.portal.service.IUserService;


@Service("loginService")
public class LoginServiceImpl extends BaseServiceImpl<UserLogin> implements ILoginService {

	private final static Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	
	@Autowired
	private SessionServiceImpl sessionService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IUserLoginDao userLoginDao;
	
	public LoginServiceImpl() {
		super(UserLogin.class);
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

	@Override
	public IBaseDao<UserLogin> getDao() {
		return userLoginDao;
	}
	
}
