package com.letv.portal.service.adminoplog;

public class AdminOpLogQueryCondition {
	private Long beginTime;
	private Long endTime;
	private String userNameKeyword;
	private String eventKeyword;
	private String logTypeNameKeyword;
	private String moduleKeyword;
	private String descriptionKeyword;

	public String getLogTypeNameKeyword() {
		return logTypeNameKeyword;
	}

	public void setLogTypeNameKeyword(String logTypeNameKeyword) {
		this.logTypeNameKeyword = logTypeNameKeyword;
	}

	public String getModuleKeyword() {
		return moduleKeyword;
	}

	public void setModuleKeyword(String moduleKeyword) {
		this.moduleKeyword = moduleKeyword;
	}

	public String getDescriptionKeyword() {
		return descriptionKeyword;
	}

	public void setDescriptionKeyword(String descriptionKeyword) {
		this.descriptionKeyword = descriptionKeyword;
	}

	public Long getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Long beginTime) {
		this.beginTime = beginTime;
	}

	public Long getEndTime() {
		return endTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public String getUserNameKeyword() {
		return userNameKeyword;
	}

	public void setUserNameKeyword(String userNameKeyword) {
		this.userNameKeyword = userNameKeyword;
	}

	public String getEventKeyword() {
		return eventKeyword;
	}

	public void setEventKeyword(String eventKeyword) {
		this.eventKeyword = eventKeyword;
	}

}
