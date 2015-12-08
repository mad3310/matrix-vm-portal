package com.letv.portal.service.openstack.password;

import com.letv.portal.service.openstack.exception.OpenStackException;

import java.security.NoSuchAlgorithmException;

public interface PasswordService {
	String userIdToPassword(String userId);
	void validateUserAdminPass(String adminPass) throws OpenStackException;
}
