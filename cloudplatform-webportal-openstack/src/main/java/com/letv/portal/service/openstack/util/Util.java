package com.letv.portal.service.openstack.util;

import java.util.Random;

public class Util {
	public static String generateRandomPassword(int length) {
		final String charactors = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuilder stringBuilder = new StringBuilder();
		for (int count = 0; count < length; count++) {
			int index = random.nextInt(charactors.length());
			stringBuilder.append(charactors.charAt(index));
		}
		return stringBuilder.toString();
	}
}
