package com.letv.portal.enumeration;

public enum UserStatus implements ByteEnum{
	NORMAL(0),
	FREEZE(1),  
	EXCEPTION(2),
	DISABLED(3),
	PENDING(4),
	ACTIVED(5);
	
	
	private final Integer dbValue;
	
	private UserStatus(Integer dbValue)
	{
		this.dbValue = dbValue;
	}
	
	@Override
	public Integer getValue() {
		return this.dbValue;
	}
}
