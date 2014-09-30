package com.letv.portal.proxy;


import com.letv.common.session.Session;
import com.letv.portal.dao.IBaseDao;
import com.letv.portal.model.UserLogin;
import com.letv.portal.model.UserModel;


public  interface ILoginProxy extends IBaseProxy<UserLogin>{
	public Session saveOrUpdateUserAndLogin(UserLogin userLogin);
	public Session createUserSession(UserModel user);
	public void logout();
	public void checkingUserStatus(String userName);
	public IBaseDao<UserLogin> getDao();

	
}
