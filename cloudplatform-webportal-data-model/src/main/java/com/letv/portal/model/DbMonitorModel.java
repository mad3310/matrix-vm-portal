package com.letv.portal.model;

public class DbMonitorModel {
	private Long id; 
	private String dbMonitorName; 
	private String nodeMonitorName; 
	private String alarm; 
	private String message; 
	private String ctime;
	private String errorRecord;  
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDbMonitorName() {
		return dbMonitorName;
	}
	public void setDbMonitorName(String dbMonitorName) {
		this.dbMonitorName = dbMonitorName;
	}
	public String getNodeMonitorName() {
		return nodeMonitorName;
	}
	public void setNodeMonitorName(String nodeMonitorName) {
		this.nodeMonitorName = nodeMonitorName;
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
	public String getCtime() {
		return ctime;
	}
	public void setCtime(String ctime) {
		this.ctime = ctime;
	}
	public String getErrorRecord() {
		return errorRecord;
	}
	public void setErrorRecord(String errorRecord) {
		this.errorRecord = errorRecord;
	} 
	
	
}
