package com.letv.portal.enumeration;

public enum SlbBackendStatus implements ByteEnum{
	NOWORK(5),
	WORK(6);
	private final Integer value;
	
	private SlbBackendStatus(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
