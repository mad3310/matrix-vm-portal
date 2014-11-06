package com.letv.portal.enumeration;

public enum ContainerMonitorStatus implements ByteEnum{
	
	ABNORMAL(5),//异常
	DEFAULT(6);	//正常 
	private final Integer value;

	private ContainerMonitorStatus(Integer value) {
		this.value = value;
	}
	@Override
	public Integer getValue() {
		 return value;
	}
}
