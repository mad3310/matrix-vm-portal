package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

public class CloudvmRegion extends BaseModel {

	private static final long serialVersionUID = -8757063845109274144L;

	private String code;
	private String displayName;
	private String type;

	public CloudvmRegion() {
	}

	public CloudvmRegion(String code, String displayName) {
		this();
		this.code = code;
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
