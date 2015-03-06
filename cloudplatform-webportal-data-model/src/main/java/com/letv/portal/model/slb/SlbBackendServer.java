package com.letv.portal.model.slb;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.SlbBackEndType;

public class SlbBackendServer extends BaseModel {
	
	private static final long serialVersionUID = -6850281761807630222L;
	
	private Long containerId;
	private SlbBackEndType type;
	private String serverName;
	private String serverIp;
	private String port;
	private Long slbId;
	
	public Long getContainerId() {
		return containerId;
	}
	public void setContainerId(Long containerId) {
		this.containerId = containerId;
	}
	public SlbBackEndType getType() {
		return type;
	}
	public void setType(SlbBackEndType type) {
		this.type = type;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerIp() {
		return serverIp;
	}
	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}
	public Long getSlbId() {
		return slbId;
	}
	public void setSlbId(Long slbId) {
		this.slbId = slbId;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}

}
