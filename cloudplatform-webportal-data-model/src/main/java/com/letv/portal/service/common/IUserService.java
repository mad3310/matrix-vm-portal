package com.letv.portal.service.common;

import com.letv.portal.model.common.UserModel;
import com.letv.portal.model.common.UserVo;


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
