package com.letv.portal.enumeration;

public enum MclusterStatus implements ByteEnum{
	DEFAULT(0),
	RUNNING(1),  
	BUILDDING(2),
	BUILDFAIL(3),
	AUDITFAIL(4);
	
	private final byte value;
	
	private MclusterStatus(int value)
	{
		this.value = (byte)value;
	}
	
	@Override
	public byte getValue() {
		return this.value;
	}
}
