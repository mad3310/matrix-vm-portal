package com.letv.portal.enumeration;

public enum ContainerMonitorStatus implements ByteEnum{
	
	NORMAL(6),//正常
	GENERAL(5),	//异常 
	SERIOUS(13);//危险 
	private final Integer value;

	private ContainerMonitorStatus(Integer value) {
		this.value = value;
	}
	@Override
	public Integer getValue() {
		 return value;
	}
}
