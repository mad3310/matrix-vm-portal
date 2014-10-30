package com.letv.portal.service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.Session;
import com.letv.portal.model.UserLogin;


public interface ILoginService extends IBaseService<UserLogin>{

	public Session saveOrUpdateUserAndLogin(UserLogin userLogin);

	public void logout();
	
	public void checkingUserStatus(String userName);
   
	public IBaseDao<UserLogin> getDao();
}
