package com.letv.portal.model.task;

import com.letv.common.model.BaseModel;

public class TemplateTaskDetail extends BaseModel {

	private static final long serialVersionUID = 4318996252018673374L;
	
	private String name;
	private String descn;
	private String beanName;
	private String params;
	private int retry;
	private int version;
	
	public String getParameters() {
		return params;
	}
	public void setParameters(String params) {
		this.params = params;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getBeanName() {
		return beanName;
	}
	public void setBeanName(String beanName) {
		this.beanName = beanName;
	}
	public int getRetry() {
		return retry;
	}
	public void setRetry(int retry) {
		this.retry = retry;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	
}
