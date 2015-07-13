package com.letv.portal.service.openstack.util;

import java.io.IOException;
import java.util.Random;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.letv.portal.service.openstack.exception.OpenStackException;

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

	public static <T> T fromJson(String json,
			@SuppressWarnings("rawtypes") TypeReference typeReference)
			throws OpenStackException {
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			throw new OpenStackException("请求数据格式错误", e);
		} catch (JsonMappingException e) {
			throw new OpenStackException("请求数据格式错误", e);
		} catch (IOException e) {
			throw new OpenStackException("后台服务错误", e);
		}
	}

}
