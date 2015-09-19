package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public class CloudvmServer extends BaseModel {

	private static final long serialVersionUID = 441262241566576508L;

	private String region;
	private String serverId;
	private String flavorId;

	public CloudvmServer() {
	}

	public CloudvmServer(String region, String serverId, String flavorId) {
		this();
		this.region = region;
		this.serverId = serverId;
		this.flavorId = flavorId;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
