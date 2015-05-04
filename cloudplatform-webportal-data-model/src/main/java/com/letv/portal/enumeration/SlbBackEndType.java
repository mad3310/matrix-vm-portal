package com.letv.portal.enumeration;

public enum SlbBackEndType implements ByteEnum{
	WEB(0),
	RDS(1),
	CUSTOM(2);
	
	private final Integer value;
	
	private SlbBackEndType(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
