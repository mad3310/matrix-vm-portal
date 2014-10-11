package com.letv.portal.service;

import com.letv.common.exception.CommonException;
import com.letv.portal.enumeration.UserStatus;
import com.letv.portal.model.UserModel;


public interface IUserService extends IBaseService<UserModel>{
	
	public void saveUserObject(UserModel user);
	
	public UserModel saveUserObjectWithSpecialName(String userName,String loginIp);
	
	public UserModel getUserByName(String userName);
	
	public void updateUserStauts(Long userid,UserStatus status);
	
	public void updateUserLoginInfo(UserModel user,String currentLoginIp);
	
	public boolean existUserByUserName(String userName);
	
	public UserModel getUserById(Long userId);
}
