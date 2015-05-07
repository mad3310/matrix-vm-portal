package com.letv.portal.enumeration;

public enum OssServerVisibility implements ByteEnum{
	PRIVATE(0),
	PUBLIC(1) ;

	private final Integer value;
	
	private OssServerVisibility(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
