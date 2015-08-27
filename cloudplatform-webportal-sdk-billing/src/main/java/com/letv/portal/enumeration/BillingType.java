package com.letv.portal.enumeration;

public enum BillingType{
	BYTIME(0,"按时间"),
	BYUSEDLADDER(1,"按用量阶梯");
	
	private int value;
	private String  descn;
	
	private BillingType(int value,String descn) {
		this.value = value;
		this.descn = descn;
	}

	public int getValue() {
		return value;
	}

	public String getDescn() {
		return descn;
	}
	
}
