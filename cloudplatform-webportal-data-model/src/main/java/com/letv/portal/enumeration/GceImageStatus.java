package com.letv.portal.enumeration;

public enum GceImageStatus implements ByteEnum{
	AVAILABLE(1),  
	NOTAVAILABLE(2);
	
	private final Integer value;
	
	private GceImageStatus(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
