package com.letv.portal.enumeration;

public enum SlbStatus implements ByteEnum{
	DEFAULT(0),
	RUNNING(1),  
	BUILDDING(2),
	BUILDFAIL(3),
	AUDITFAIL(4),
	ABNORMAL(5),
	NORMAL(6),
	STARTING(7),
	STOPPING(8),
	STOPED(9);
	
	private final Integer value;
	
	private SlbStatus(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
