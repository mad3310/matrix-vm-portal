package com.letv.portal.proxy.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.IUserDao;
import com.letv.portal.enumeration.UserStatus;
import com.letv.portal.model.UserModel;
import com.letv.portal.proxy.IUserProxy;
import com.letv.portal.service.IBaseService;


@Component
public class UserProxyImpl extends BaseProxyImpl<UserModel> implements IUserProxy{

	@Override
	public void saveUserObject(UserModel user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UserModel saveUserObjectWithSpecialName(String userName,
			String loginIp) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserModel getUserByName(String userName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUserStauts(Long userid, UserStatus status) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateUserLoginInfo(UserModel user, String currentLoginIp) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean existUserByUserName(String userName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IBaseService<UserModel> getService() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
