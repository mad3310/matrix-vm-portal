package com.letv.portal.model;

import java.sql.Timestamp;

public class BaseModel implements IEntity,ISoftDelete{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5822755615614280336L;
	
	private String id;
	
	private boolean deleted;
	
	private Timestamp createTime;
	private Long createUser;
	private Timestamp updateTime;
	private Long updateUser;

	@Override
	public boolean isDeleted() {
		return deleted;
	}

	@Override
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;		
	}

	@Override
	public String getId() {
		return id;
	}

	@Deprecated
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public Timestamp getCreateTime() {
		return createTime;
	}
	
	@Override
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public Long getCreateUser() {
		return createUser;
	}
	
	@Override
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	
	@Override
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	
	@Override
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	
	@Override
	public Long getUpdateUser() {
		return updateUser;
	}
	
	@Override
	public void setUpdateUser(Long updateUser) {
		this.updateUser = updateUser;
	}
}
