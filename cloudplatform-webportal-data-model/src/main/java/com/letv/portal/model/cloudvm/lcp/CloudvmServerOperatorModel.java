package com.letv.portal.model.cloudvm.lcp;

import com.letv.common.model.BaseModel;


public class CloudvmServerOperatorModel extends BaseModel {

    private static final long serialVersionUID = 441262241566576508L;

    private Long serverId;
    private Long userId;
    
	public Long getServerId() {
		return serverId;
	}
	public void setServerId(Long serverId) {
		this.serverId = serverId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
    
	
    
}
