package com.letv.portal.enumeration;

public enum BackupStatus implements ByteEnum{
	FAILD(0),
	SUCCESS(1),
	BUILDING(2),
	WAITTING(3);
	
	private final Integer value;
	
	private BackupStatus(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}

}
