package com.letv.portal.enumeration;

public enum MclusterType implements ByteEnum{
	AUTO(0),
	HAND(1);
	
	private final Integer value;
	
	private MclusterType(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
