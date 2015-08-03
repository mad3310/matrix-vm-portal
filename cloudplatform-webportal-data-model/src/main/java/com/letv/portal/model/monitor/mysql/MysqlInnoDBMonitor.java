package com.letv.portal.model.monitor.mysql;

import com.letv.common.model.BaseModel;

/**
 * InnoDB监控实体类
 *
 */
public class MysqlInnoDBMonitor extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	private String hostIp;//内部IP
	private String hostTag;//标签
	private Float innodbBufferPoolSize;//缓存区大小
	private Float innodbBufferReadHits;//Buffer命中率
	private Float innodbRowsRead;//每秒读取行数
	private Float innodbRowsInsert;//每秒增加行数
	private Float innodbRowsUpdate;//每秒修改行数
	private Float innodbRowsDelete ;//每秒删除行数
	private String descn;//描述
	
	public Float getInnodbBufferReadHits() {
		return innodbBufferReadHits;
	}
	public void setInnodbBufferReadHits(Float innodbBufferReadHits) {
		this.innodbBufferReadHits = innodbBufferReadHits;
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
	public Float getInnodbBufferPoolSize() {
		return innodbBufferPoolSize;
	}
	public void setInnodbBufferPoolSize(Float innodbBufferPoolSize) {
		this.innodbBufferPoolSize = innodbBufferPoolSize;
	}
	public Float getInnodbRowsRead() {
		return innodbRowsRead;
	}
	public void setInnodbRowsRead(Float innodbRowsRead) {
		this.innodbRowsRead = innodbRowsRead;
	}
	public Float getInnodbRowsInsert() {
		return innodbRowsInsert;
	}
	public void setInnodbRowsInsert(Float innodbRowsInsert) {
		this.innodbRowsInsert = innodbRowsInsert;
	}
	public Float getInnodbRowsUpdate() {
		return innodbRowsUpdate;
	}
	public void setInnodbRowsUpdate(Float innodbRowsUpdate) {
		this.innodbRowsUpdate = innodbRowsUpdate;
	}
	public Float getInnodbRowsDelete() {
		return innodbRowsDelete;
	}
	public void setInnodbRowsDelete(Float innodbRowsDelete) {
		this.innodbRowsDelete = innodbRowsDelete;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
