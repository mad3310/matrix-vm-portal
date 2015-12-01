package com.letv.portal.service.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.CommonException;
import com.letv.portal.dao.IUserDao;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.oauth.IUcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<UserModel> implements IUserService{

	@Autowired
	private IUserDao userDao;

	public UserServiceImpl() {
		super(UserModel.class);
	}

	public void saveUserObject(UserModel user) {
		Date date = new Date();
		user.setLastLoginTime(date);
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
		user.setEmail(email);
		saveUserObject(user);
		return user;
	}

	@Override
	public void updateUserLoginInfo(UserModel user,String currentLoginIp) {
		if(null == user)
			throw new CommonException("User object is not null");

		Date lastLoginTime = new Date();
		user.setLastLoginIp(currentLoginIp);
		user.setLastLoginTime(lastLoginTime);
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

	@Override
	public UserVo getUcUserById(Long userId) {
		UserModel userModel = this.getUserById(userId);
		UserVo user = new UserVo();
		user.setUserId(userId);
		user.setUsername(userModel.getUserName());
		user.setEmail(userModel.getEmail());
		user.setMobile(userModel.getMobile());
		return user;
	}

	@Override
	public UserModel selectByOauthId(String oauthId) {
		return this.userDao.selectByOauthId(oauthId);
	}

	@Override
	public UserModel selectByUcId(Long ucId) {
		return this.userDao.selectByUcId(ucId);
	}

	@Override
	public Long getUserIdByUcId(Long ucId) {
		UserModel userModel = this.selectByUcId(ucId);
		if(userModel == null)
			return null;
		return userModel.getId();
	}

}
