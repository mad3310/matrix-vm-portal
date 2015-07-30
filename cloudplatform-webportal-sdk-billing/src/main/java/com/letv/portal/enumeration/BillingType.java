package com.letv.portal.enumeration;

public enum BillingType{
	BYTIME(0,"按时间"),
	BYUSEDLADDER(1,"按用量阶梯");
	
	private final Integer value;
	private final String  descn;
	
	private BillingType(Integer value,String descn) {
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
