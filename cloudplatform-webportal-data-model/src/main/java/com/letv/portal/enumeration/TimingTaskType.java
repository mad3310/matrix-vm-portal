package com.letv.portal.enumeration;

public enum TimingTaskType implements ByteEnum{
	CRON(0),
	INTERVAL(1);
	
	private final Integer value;
	
	private TimingTaskType(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}

}
