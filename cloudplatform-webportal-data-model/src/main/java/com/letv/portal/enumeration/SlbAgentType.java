package com.letv.portal.enumeration;

public enum SlbAgentType implements ByteEnum{
	HTTP(0),
	TCP(1),
	MYSQL(2);
	
	private final Integer value;
	
	private SlbAgentType(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
