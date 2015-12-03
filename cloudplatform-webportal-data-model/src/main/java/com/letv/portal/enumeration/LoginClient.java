package com.letv.portal.enumeration;

public enum LoginClient{
	APP(0,"客户端登录"),
	ADMIN(1,"管理端登录");

	private final Integer value;
	private final String  descn;

	private LoginClient(Integer value,String descn) {
		this.value = value;
		this.descn = descn;
	}

	public Integer getValue() {
		return value;
	}

	public String getDescn() {
		return descn;
	}

}
