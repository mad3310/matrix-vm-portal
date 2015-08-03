package com.letv.portal.model.monitor.mysql;

import com.letv.common.model.BaseModel;

/**
 * 键缓存监控实体类
 *
 */
public class MysqlKeyBufferMonitor extends BaseModel {

	private static final long serialVersionUID = 4894529482499154716L;

	private String hostIp;//内部IP
	private String hostTag;//标签
	private Float keyBufferSize;//索引缓冲区大小
	private Float sortBufferSize;//每个线程排序所需缓冲
	private Float joinBufferSize;//Join操作使用内存
	private Integer keyBlocksUnused;//未使用的缓存簇
	private Integer keyBlocksUsed ;//曾经用到的最大的blocks数
	private Integer keyBlocksNotFlushed;//键缓存内已经更改但还没有清空到硬盘上的键的数据块数量
	private Float keyBlocksUsedRate;//未使用的缓存簇率
	private Float keyBufferReadRate;
	private Float keyBufferWriteRate;
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
	public Float getKeyBufferSize() {
		return keyBufferSize;
	}
	public void setKeyBufferSize(Float keyBufferSize) {
		this.keyBufferSize = keyBufferSize;
	}
	public Float getSortBufferSize() {
		return sortBufferSize;
	}
	public void setSortBufferSize(Float sortBufferSize) {
		this.sortBufferSize = sortBufferSize;
	}
	public Float getJoinBufferSize() {
		return joinBufferSize;
	}
	public void setJoinBufferSize(Float joinBufferSize) {
		this.joinBufferSize = joinBufferSize;
	}
	public Integer getKeyBlocksUnused() {
		return keyBlocksUnused;
	}
	public void setKeyBlocksUnused(Integer keyBlocksUnused) {
		this.keyBlocksUnused = keyBlocksUnused;
	}
	public Integer getKeyBlocksUsed() {
		return keyBlocksUsed;
	}
	public void setKeyBlocksUsed(Integer keyBlocksUsed) {
		this.keyBlocksUsed = keyBlocksUsed;
	}
	public Integer getKeyBlocksNotFlushed() {
		return keyBlocksNotFlushed;
	}
	public void setKeyBlocksNotFlushed(Integer keyBlocksNotFlushed) {
		this.keyBlocksNotFlushed = keyBlocksNotFlushed;
	}
	public Float getKeyBlocksUsedRate() {
		return keyBlocksUsedRate;
	}
	public void setKeyBlocksUsedRate(Float keyBlocksUsedRate) {
		this.keyBlocksUsedRate = keyBlocksUsedRate;
	}
	public Float getKeyBufferReadRate() {
		return keyBufferReadRate;
	}
	public void setKeyBufferReadRate(Float keyBufferReadRate) {
		this.keyBufferReadRate = keyBufferReadRate;
	}
	public Float getKeyBufferWriteRate() {
		return keyBufferWriteRate;
	}
	public void setKeyBufferWriteRate(Float keyBufferWriteRate) {
		this.keyBufferWriteRate = keyBufferWriteRate;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	
}
