package com.letv.portal.model.invitecode;

import com.letv.common.model.BaseModel;

public class InviteCode extends BaseModel {

	private static final long serialVersionUID = -8757063845109274144L;

	private String inviteCode;//邀请码
	private Long userId;//用户id
	private String used;//是否使用:1-使用，0-未使用
	private String descn;//描述
	
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	

}
