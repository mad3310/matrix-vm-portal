package com.letv.portal.enumeration;

public enum UserStatus implements ByteEnum{
	NORMAL(0),
	FREEZE(1),  
	EXCEPTION(2),
	DISABLED(3),
	PENDING(4),
	ACTIVED(5);
	
	
	private final byte dbValue;
	
	private UserStatus(int dbValue)
	{
		this.dbValue = (byte)dbValue;
	}
	
	@Override
	public byte getDbValue() {
		return this.dbValue;
	}
}
