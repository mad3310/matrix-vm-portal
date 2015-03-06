package com.letv.portal.enumeration;

public enum ServiceType implements ByteEnum{
	RDS(0),
	SLB(1),
	WEBSERVER(2);
	
	private final Integer value;
	
	private ServiceType(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}

}
