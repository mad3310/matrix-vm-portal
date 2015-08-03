package com.letv.portal.model.monitor.mysql;

import com.letv.common.model.BaseModel;

/**
 * 健康监控实体类
 *
 */
public class MysqlHealthMonitor extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	private String hostIp;//内部IP
	private String hostTag;//标签
	private String role;//角色
	private Float runTime;//运行时间
	private String version;//版本
	private Integer connectCount;//连接数
	private Integer activityCount;//活动数
	private Integer waitCount;//等待数
	private Float send;//发送量
	private Float recv;//接收量
	private Float queryPs;//每秒查询
	private Float transactionPs;//每秒事物
	private Integer slowQueryCount;//慢查询数
	private Float cpu;//cpu
	private Float memory;//内存
	private String descn;//描述
	
	
	public Integer getSlowQueryCount() {
		return slowQueryCount;
	}
	public void setSlowQueryCount(Integer slowQueryCount) {
		this.slowQueryCount = slowQueryCount;
	}
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
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public Float getRunTime() {
		return runTime;
	}
	public void setRunTime(Float runTime) {
		this.runTime = runTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getConnectCount() {
		return connectCount;
	}
	public void setConnectCount(Integer connectCount) {
		this.connectCount = connectCount;
	}
	public Integer getActivityCount() {
		return activityCount;
	}
	public void setActivityCount(Integer activityCount) {
		this.activityCount = activityCount;
	}
	public Integer getWaitCount() {
		return waitCount;
	}
	public void setWaitCount(Integer waitCount) {
		this.waitCount = waitCount;
	}
	public Float getSend() {
		return send;
	}
	public void setSend(Float send) {
		this.send = send;
	}
	public Float getRecv() {
		return recv;
	}
	public void setRecv(Float recv) {
		this.recv = recv;
	}
	public Float getQueryPs() {
		return queryPs;
	}
	public void setQueryPs(Float queryPs) {
		this.queryPs = queryPs;
	}
	public Float getTransactionPs() {
		return transactionPs;
	}
	public void setTransactionPs(Float transactionPs) {
		this.transactionPs = transactionPs;
	}
	public Float getCpu() {
		return cpu;
	}
	public void setCpu(Float cpu) {
		this.cpu = cpu;
	}
	public Float getMemory() {
		return memory;
	}
	public void setMemory(Float memory) {
		this.memory = memory;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
