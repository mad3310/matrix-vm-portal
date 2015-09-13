package com.letv.portal.service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.Session;
import com.letv.portal.model.UserLogin;


public interface ILoginService extends IBaseService<UserLogin>{

	public void logout();
	
	public IBaseDao<UserLogin> getDao();
}
