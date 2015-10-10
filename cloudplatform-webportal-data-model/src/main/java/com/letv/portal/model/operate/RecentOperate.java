package com.letv.portal.model.operate;

import com.letv.common.model.BaseModel;

public class RecentOperate extends BaseModel {

	private static final long serialVersionUID = -8757063845109274144L;

	private String action;//动作
	private String content;//内容
	private String descn;// 描述
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}

}
