package com.letv.portal.service;

import com.letv.portal.model.UserModel;


public interface IUserService extends IBaseService<UserModel>{
	
	public void saveUserObject(UserModel user);
	
	public UserModel saveUserObjectWithSpecialName(String userName,String loginIp,String email,Long ucId);
	
	public void updateUserLoginInfo(UserModel user,String currentLoginIp);
	
	public UserModel getUserById(Long userId);

	public UserModel getUserByNameAndEmailOrUcId(String userNamePassport, String email,Long ucId);

	public UserModel getUserByUcId(Long ucId);
}
