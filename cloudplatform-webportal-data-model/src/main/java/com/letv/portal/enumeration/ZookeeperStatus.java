package com.letv.portal.enumeration;

public enum ZookeeperStatus implements ByteEnum{
	AVAILABLE(1),  
	NOTAVAILABLE(2);
	
	private final Integer value;
	
	private ZookeeperStatus(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}
}
