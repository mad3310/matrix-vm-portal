package com.letv.portal.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.CommonException;
import com.letv.common.exception.ValidateException;
import com.letv.portal.dao.IUserDao;
import com.letv.portal.enumeration.UserStatus;
import com.letv.portal.model.UserModel;
import com.letv.portal.service.IUserService;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserModel> implements IUserService{
	
	@Autowired
	private IUserDao userDao;
	
	public UserServiceImpl() {
		super(UserModel.class);
	}

	public void saveUserObject(UserModel user) {
		Date date = new Date();
		user.setRegisterDate(date);
		user.setLastLoginTime(date);
		user.setLastLoginIp(user.getCurrentLoginIp());
		user.setStatus(UserStatus.NORMAL);
		user.setDeleted(true);
		//TODO re-factor
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		user.setCreateUser(Long.valueOf(0L));
		user.setCreateTime(timestamp);
		user.setUpdateUser(Long.valueOf(0L));
		user.setUpdateTime(timestamp);
		super.insert(user);
	}
	
	@Override
	public UserModel getUserById(Long userid)
	{
		UserModel user = super.selectById(userid);
		if(null == user)
			throw new CommonException("User不存在，user id："+userid);
		return user;
	}
	
	@Override
	public UserModel saveUserObjectWithSpecialName(String userName,String loginIp,String email) {
		UserModel user = new UserModel();
		user.setUserName(userName);
		user.setCurrentLoginIp(loginIp);
		user.setEmail(email);
		saveUserObject(user);
		return user;
	}
	
	@Override
	public void updateUserLoginInfo(UserModel user,String currentLoginIp) {
		if(null == user)
			throw new CommonException("User object is not null");
		
		Date lastLoginTime = user.getCurrentLoginTime();
		user.setLastLoginIp(user.getCurrentLoginIp());
		user.setLastLoginTime(lastLoginTime);
		user.setCurrentLoginIp(currentLoginIp);
		user.setCurrentLoginTime(new Date());
		
		super.update(user);
	}

	@Override
	public IBaseDao<UserModel> getDao() {
		return userDao;
	}

	@Override
	public UserModel getUserByNameAndEmail(String userNamePassport, String email) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("userName",userNamePassport);
		map.put("email",email);
		List<UserModel> users = selectByMap(map);
		if(null == users || users.isEmpty())
			return null;
		
		return users.get(0);
	}

}
