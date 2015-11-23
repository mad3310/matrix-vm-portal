package com.letv.portal.model.monitor.mysql;

import com.letv.common.model.BaseModel;

/**
 * 资源监控实体类
 *
 */
public class MysqlResourceMonitor extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	private String hostIp;//内部IP
	private String hostTag;//标签
	private Integer maxConnect;//最大连接数
	private Integer maxConnectError;//最大错误连接数
	private Integer maxOpenFile ;//最大打开文件数
	private Integer hadOpenFile;//已打开文件数
	private Integer cacheTableCount;//表缓存数
	private Integer cacheTableNohitCount;//表缓存未命中数
	private Integer hadOpenTable;//已打开表
	private String descn;//描述
	
	public Integer getCacheTableNohitCount() {
		return cacheTableNohitCount;
	}
	public void setCacheTableNohitCount(Integer cacheTableNohitCount) {
		this.cacheTableNohitCount = cacheTableNohitCount;
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
	public Integer getMaxConnect() {
		return maxConnect;
	}
	public void setMaxConnect(Integer maxConnect) {
		this.maxConnect = maxConnect;
	}
	public Integer getMaxConnectError() {
		return maxConnectError;
	}
	public void setMaxConnectError(Integer maxConnectError) {
		this.maxConnectError = maxConnectError;
	}
	public Integer getMaxOpenFile() {
		return maxOpenFile;
	}
	public void setMaxOpenFile(Integer maxOpenFile) {
		this.maxOpenFile = maxOpenFile;
	}
	public Integer getHadOpenFile() {
		return hadOpenFile;
	}
	public void setHadOpenFile(Integer hadOpenFile) {
		this.hadOpenFile = hadOpenFile;
	}
	public Integer getCacheTableCount() {
		return cacheTableCount;
	}
	public void setCacheTableCount(Integer cacheTableCount) {
		this.cacheTableCount = cacheTableCount;
	}
	public Integer getHadOpenTable() {
		return hadOpenTable;
	}
	public void setHadOpenTable(Integer hadOpenTable) {
		this.hadOpenTable = hadOpenTable;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
