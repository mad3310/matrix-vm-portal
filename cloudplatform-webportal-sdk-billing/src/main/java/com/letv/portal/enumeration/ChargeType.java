package com.letv.portal.enumeration;

public enum ChargeType{
	BYTIME(0,"包年包月"),
	BYUSEDLADDER(1,"按需");
	
	private final Integer value;
	private final String  descn;
	
	private ChargeType(Integer value,String descn) {
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
