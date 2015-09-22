package com.letv.portal.service;

import com.letv.portal.model.UserModel;
import com.letv.portal.model.UserVo;


public interface IUserService extends IBaseService<UserModel>{
	
	public void saveUserObject(UserModel user);
	
	public UserModel saveUserObjectWithSpecialName(String userName,String loginIp,String email);
	
	public void updateUserLoginInfo(UserModel user,String currentLoginIp);
	
	public UserModel getUserById(Long userId);
	
	public UserModel getUserByNameAndEmail(String userNamePassport, String email);
	
	public UserVo getUcUserById(Long userId);
}
