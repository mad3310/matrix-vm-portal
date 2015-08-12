package com.letv.portal.service.openstack.password;

import java.security.NoSuchAlgorithmException;

public interface PasswordService {
	public String userIdToPassword(String userId) throws NoSuchAlgorithmException;
}
