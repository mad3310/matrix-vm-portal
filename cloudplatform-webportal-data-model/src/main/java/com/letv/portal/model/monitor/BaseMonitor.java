package com.letv.portal.model.monitor;

import com.letv.portal.enumeration.MonitorStatus;

public class BaseMonitor {
	private int result;
	
	public BaseMonitor() {
		this.result = MonitorStatus.TIMEOUT.getValue();
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}
	
}
