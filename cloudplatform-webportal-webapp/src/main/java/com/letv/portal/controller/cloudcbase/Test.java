package com.letv.portal.controller.cloudcbase;

import com.letv.common.util.HttpClient;

public class Test {
	public static void main() {
		
		String result = HttpClient.get("http://10.154.156.57:8091/pools",
				"Administrator", "password");
		
		System.out.println(result);
	}
}
