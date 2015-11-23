package com.letv.portal.model.monitor.mysql;

import com.letv.common.model.BaseModel;

/**
 * Galera监控实体类
 *
 */
public class MysqlGaleraMonitor extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	private String hostIp;//内部IP
	private String hostTag;//标签
	private Float wsrepLocalFail;
	private Float wsrepLocalAbort;
	private Float wsrepLocalReplays;
	private Float wsrepReplicated;
	private Float wsrepRepBytes;
	private Float wsrepReceived;
	private Float wsrepRecBytes;
	private Float wsrepFlowControlPaused;
	private Float wsrepFlowControlSent;
	private Float wsrepFlowControlRecv;
	private String descn;//描述
	
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public String getHostTag() {
		return hostTag;
	}
	public void setHostTag(String hostTag) {
		this.hostTag = hostTag;
	}
	public Float getWsrepLocalFail() {
		return wsrepLocalFail;
	}
	public void setWsrepLocalFail(Float wsrepLocalFail) {
		this.wsrepLocalFail = wsrepLocalFail;
	}
	public Float getWsrepLocalAbort() {
		return wsrepLocalAbort;
	}
	public void setWsrepLocalAbort(Float wsrepLocalAbort) {
		this.wsrepLocalAbort = wsrepLocalAbort;
	}
	public Float getWsrepLocalReplays() {
		return wsrepLocalReplays;
	}
	public void setWsrepLocalReplays(Float wsrepLocalReplays) {
		this.wsrepLocalReplays = wsrepLocalReplays;
	}
	public Float getWsrepReplicated() {
		return wsrepReplicated;
	}
	public void setWsrepReplicated(Float wsrepReplicated) {
		this.wsrepReplicated = wsrepReplicated;
	}
	public Float getWsrepRepBytes() {
		return wsrepRepBytes;
	}
	public void setWsrepRepBytes(Float wsrepRepBytes) {
		this.wsrepRepBytes = wsrepRepBytes;
	}
	public Float getWsrepReceived() {
		return wsrepReceived;
	}
	public void setWsrepReceived(Float wsrepReceived) {
		this.wsrepReceived = wsrepReceived;
	}
	public Float getWsrepRecBytes() {
		return wsrepRecBytes;
	}
	public void setWsrepRecBytes(Float wsrepRecBytes) {
		this.wsrepRecBytes = wsrepRecBytes;
	}
	public Float getWsrepFlowControlPaused() {
		return wsrepFlowControlPaused;
	}
	public void setWsrepFlowControlPaused(Float wsrepFlowControlPaused) {
		this.wsrepFlowControlPaused = wsrepFlowControlPaused;
	}
	public Float getWsrepFlowControlSent() {
		return wsrepFlowControlSent;
	}
	public void setWsrepFlowControlSent(Float wsrepFlowControlSent) {
		this.wsrepFlowControlSent = wsrepFlowControlSent;
	}
	public Float getWsrepFlowControlRecv() {
		return wsrepFlowControlRecv;
	}
	public void setWsrepFlowControlRecv(Float wsrepFlowControlRecv) {
		this.wsrepFlowControlRecv = wsrepFlowControlRecv;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
