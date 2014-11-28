package com.letv.portal.enumeration;

public enum ContainerMonitorStatus implements ByteEnum{
	
	NORMAL(6),//正常
	GENERAL(13),//异常 
	SERIOUS(14),//危险 
	CRASH(5);//宕机
	private final Integer value;

	private ContainerMonitorStatus(Integer value) {
		this.value = value;
	}
	@Override
	public Integer getValue() {
		 return value;
	}
}
