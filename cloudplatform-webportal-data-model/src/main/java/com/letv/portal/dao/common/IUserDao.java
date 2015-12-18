package com.letv.portal.dao.common;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.common.UserModel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userDao")
public interface IUserDao extends IBaseDao<UserModel> {

	List<UserModel> selectByOauthId(String oauthId);
	UserModel selectByUcId(Long ucId);
	Long selectUserIdByUcId(Long ucId);
}
