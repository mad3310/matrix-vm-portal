package com.letv.portal.service.openstack.password.impl;

import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.portal.service.openstack.password.PasswordService;

@Service("passwordService")
public class PasswordServiceImpl implements PasswordService {

	@Value("openstack.user.password.salt")
	private String userPasswordSalt;

	@SuppressWarnings("deprecation")
	public String userIdToPassword(String userId)
			throws NoSuchAlgorithmException {
		return DigestUtils.sha512Hex((userId + userPasswordSalt)
				.getBytes(Charsets.UTF_8));
	}
}
