package com.letv.portal.service.openstack.password;

import com.letv.portal.service.openstack.exception.OpenStackException;

import java.security.NoSuchAlgorithmException;

public interface PasswordService {
	public String userIdToPassword(String userId) throws NoSuchAlgorithmException;
	void validateUserAdminPass(String adminPass) throws OpenStackException;
}
