package com.letv.portal.proxy;

import com.letv.common.session.Session;
import com.letv.portal.model.UserLogin;

public interface ILoginProxy {
	Session saveOrUpdateUserBySession(Session session);

	@Deprecated
	Session saveOrUpdateUserAndLogin(UserLogin userLogin);

	void logout();

	Session login(String clientId,String clientSecret);
}
