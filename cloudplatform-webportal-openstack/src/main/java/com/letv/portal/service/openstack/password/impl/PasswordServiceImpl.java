package com.letv.portal.service.openstack.password.impl;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.password.PasswordService;
import com.letv.portal.service.openstack.util.constants.Constants;
import com.letv.portal.service.openstack.util.constants.ValidationRegex;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

@Service("passwordService")
public class PasswordServiceImpl implements PasswordService {

	@Value("${openstack.keystone.user.password.salt}")
	private String userPasswordSalt;

	@SuppressWarnings("deprecation")
	public String userIdToPassword(String userId) {
		return DigestUtils.sha512Hex((userId + userPasswordSalt)
				.getBytes(Charsets.UTF_8));
	}

	@Override
	public void validateUserAdminPass(String adminPass) throws OpenStackException {
		if(!adminPass.matches(ValidationRegex.password)){
			throw new UserOperationException(
					"User admin password is not valid.",
					"管理员密码必须为" + ValidationRegex.passwordMessage);
		}
	}

	public static void main(String[] args) throws NoSuchAlgorithmException {
		PasswordServiceImpl i = new PasswordServiceImpl();
		Scanner scanner = new Scanner(System.in);
		try {
			i.userPasswordSalt = scanner.nextLine();
			String line = scanner.nextLine();
			String pw = i.userIdToPassword(line);
			System.out.println(pw);
		} finally {
			scanner.close();
		}
	}
}
