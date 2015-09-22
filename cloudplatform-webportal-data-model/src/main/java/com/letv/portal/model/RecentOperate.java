package com.letv.portal.model;

import com.letv.common.model.BaseModel;
   
public class RecentOperate extends BaseModel{

	private static final long serialVersionUID = 5624339961682186807L;
	
	private String action;
	private String content;
	
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
	
}
