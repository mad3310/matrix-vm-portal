package com.letv.portal.dao;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.UserModel;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public interface IUserDao extends IBaseDao<UserModel> {

	UserModel selectByOauthId(String oauthId);
	UserModel selectByUcId(Long ucId);
	Long selectUserIdByUcId(Long ucId);
}
