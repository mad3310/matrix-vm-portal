package com.letv.portal.enumeration;

public enum BuildStatus implements ByteEnum{
	SUCCESS(1),
	FAIL(0),
	BUILDING(2),
	WAITTING(3);
	
	private final Integer value;
	
	private BuildStatus(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
