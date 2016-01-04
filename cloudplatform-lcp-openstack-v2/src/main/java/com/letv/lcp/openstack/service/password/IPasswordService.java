package com.letv.lcp.openstack.service.password;


import com.letv.lcp.openstack.exception.OpenStackException;

public interface IPasswordService {
	String userIdToPassword(String userId);
	void validateUserAdminPass(String adminPass) throws OpenStackException;
}
