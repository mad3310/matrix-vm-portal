package com.letv.portal.enumeration;

public enum HostType implements ByteEnum{
	MASTER(0),
	SLAVE(1);
	
	private final Integer value;
	
	private HostType(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
