package com.letv.portal.model.task;

import com.letv.portal.enumeration.ByteEnum;

public enum TaskExecuteStatus implements ByteEnum{
	FAILED(0),
	SUCCESS(1),
	DOING(2),
	WAITTING(3),
	UNDO(4);
	
	private final Integer value;
	
	private TaskExecuteStatus(Integer value) {
		this.value = value;
	}
	
	@Override
	public Integer getValue() {
		return this.value;
	}

}
