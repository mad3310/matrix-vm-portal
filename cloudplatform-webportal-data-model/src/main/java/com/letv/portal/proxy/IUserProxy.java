package com.letv.portal.proxy;

import com.letv.portal.enumeration.UserStatus;
import com.letv.portal.model.UserModel;


public interface IUserProxy extends IBaseProxy<UserModel>{
	
	public void saveUserObject(UserModel user);
	
	public UserModel saveUserObjectWithSpecialName(String userName,String loginIp);
	
	public UserModel getUserByName(String userName);
	
	public void updateUserStauts(Long userid,UserStatus status);
	
	public void updateUserLoginInfo(UserModel user,String currentLoginIp);
	
	public boolean existUserByUserName(String userName);
}
