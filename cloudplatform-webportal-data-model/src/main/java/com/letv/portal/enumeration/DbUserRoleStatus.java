package com.letv.portal.enumeration;

public enum DbUserRoleStatus implements ByteEnum{
	MANAGER(1),
	RO(2),
	WR(3);
	private final Integer value;
	
	private DbUserRoleStatus(Integer value)
	{
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}

}
