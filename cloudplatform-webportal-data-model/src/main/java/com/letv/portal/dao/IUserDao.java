package com.letv.portal.dao;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.UserModel;

public interface IUserDao extends IBaseDao<UserModel> {

	UserModel getUserByUcId(Long ucId);
	  
}
