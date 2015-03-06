package com.letv.portal.model.slb;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.SlbAgentType;

public class SlbConfig extends BaseModel {
	
	private static final long serialVersionUID = -2455777525390244840L;
	
	private Long slbId;
	
	private SlbAgentType agentType;
	private String frontPort;
	
	private SlbServer slbServer;
	
	public Long getSlbId() {
		return slbId;
	}
	public void setSlbId(Long slbId) {
		this.slbId = slbId;
	}
	public SlbAgentType getAgentType() {
		return agentType;
	}
	public void setAgentType(SlbAgentType agentType) {
		this.agentType = agentType;
	}
	public String getFrontPort() {
		return frontPort;
	}
	public void setFrontPort(String frontPort) {
		this.frontPort = frontPort;
	}
	public SlbServer getSlbServer() {
		return slbServer;
	}
	public void setSlbServer(SlbServer slbServer) {
		this.slbServer = slbServer;
	}
	
}
