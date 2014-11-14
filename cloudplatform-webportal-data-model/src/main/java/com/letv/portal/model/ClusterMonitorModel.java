package com.letv.portal.model;

public class ClusterMonitorModel {
	private Long id; 
	private String monitorName; 
	private String alarm; 
	private String message;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
     
	public String getMonitorName() {
		return monitorName;
	}
	public void setMonitorName(String monitorName) {
		this.monitorName = monitorName;
	}
	public String getAlarm() {
		return alarm;
	}
	public void setAlarm(String alarm) {
		this.alarm = alarm;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	} 
	
	
}
