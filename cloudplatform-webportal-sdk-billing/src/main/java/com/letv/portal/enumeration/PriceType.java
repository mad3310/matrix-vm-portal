package com.letv.portal.enumeration;

public enum PriceType{
	BYTIME(0,"修改价格"),
	BYUSEDLADDER(1,"原价");
	
	private final Integer value;
	private final String  descn;
	
	private PriceType(Integer value,String descn) {
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
