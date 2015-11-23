package com.letv.portal.model.monitor.mysql;

import com.letv.common.model.BaseModel;

/**
 * 数据库大小监控实体类
 *
 */
public class MysqlDbSpaceMonitor extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	private String hostIp;//内部IP
	private String hostTag;//标签
	private String name;//表名称
	private Float size;//表大小
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getSize() {
		return size;
	}
	public void setSize(Float size) {
		this.size = size;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
