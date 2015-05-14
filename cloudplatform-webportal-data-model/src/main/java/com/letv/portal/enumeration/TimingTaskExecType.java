package com.letv.portal.enumeration;

public enum TimingTaskExecType implements ByteEnum{
	PYTHON(0);
	
	private final Integer value;
	
	private TimingTaskExecType(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}

}
