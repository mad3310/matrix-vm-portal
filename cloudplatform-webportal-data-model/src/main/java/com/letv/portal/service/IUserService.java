package com.letv.portal.service;

import com.letv.common.session.Session;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.UserVo;


public interface IUserService extends IBaseService<UserModel>{

    void insertByUcId(Long ucId,UserModel userModel);
    UserModel saveUserObjectWithSpecialName(String userName, String loginIp, String email);

	void updateUserLoginInfo(UserModel user, String currentLoginIp);

	UserModel getUserById(Long userId);

	UserModel getUserByNameAndEmail(String userNamePassport, String email);

	UserVo getUcUserById(Long userId);

	UserModel selectByOauthId(String oauthId);
	UserModel selectByUcId(Long ucId);
	Long getUserIdByUcId(Long ucId);
	Long getUcIdByUserId(Long userId);
}
