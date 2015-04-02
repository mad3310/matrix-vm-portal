package com.letv.portal.model.slb;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.ServiceType;

public class SlbContainer extends BaseModel {
	
	private static final long serialVersionUID = 6663970534108178228L;

	private String containerName; //节点名称  
	private String mountDir; //挂载路径
	private String zookeeperId;
	private String ipAddr; //节点ip  
	private String gateAddr; //网关
	private String ipMask; //子网掩码
	private String type; // VIP or normal
	private Integer diskSize; //磁盘大小
	private Integer coresNumber; //cpu内核数
	private Integer cpuSpeed; //cpu速度 
	private Integer memorySize; //内存大小
	private Integer status; //状态
	private String zabbixHosts;//对应zabbix删除container时候需要的标识
	private String containerUuid;//调用container用的UUID
	
	private ServiceType serviceType;
	
	private String hostIp; //ip
	private Long hostId;  //所属host
	private Long slbClusterId; //所属cluster
	
	public String getContainerName() {
		return containerName;
	}
	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	public String getMountDir() {
		return mountDir;
	}
	public void setMountDir(String mountDir) {
		this.mountDir = mountDir;
	}
	public String getZookeeperId() {
		return zookeeperId;
	}
	public void setZookeeperId(String zookeeperId) {
		this.zookeeperId = zookeeperId;
	}
	public String getIpAddr() {
		return ipAddr;
	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public String getGateAddr() {
		return gateAddr;
	}
	public void setGateAddr(String gateAddr) {
		this.gateAddr = gateAddr;
	}
	public String getIpMask() {
		return ipMask;
	}
	public void setIpMask(String ipMask) {
		this.ipMask = ipMask;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}
	public Integer getCoresNumber() {
		return coresNumber;
	}
	public void setCoresNumber(Integer coresNumber) {
		this.coresNumber = coresNumber;
	}
	public Integer getCpuSpeed() {
		return cpuSpeed;
	}
	public void setCpuSpeed(Integer cpuSpeed) {
		this.cpuSpeed = cpuSpeed;
	}
	public Integer getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Integer memorySize) {
		this.memorySize = memorySize;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getZabbixHosts() {
		return zabbixHosts;
	}
	public void setZabbixHosts(String zabbixHosts) {
		this.zabbixHosts = zabbixHosts;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public Long getHostId() {
		return hostId;
	}
	public void setHostId(Long hostId) {
		this.hostId = hostId;
	}
	public Long getSlbClusterId() {
		return slbClusterId;
	}
	public void setSlbClusterId(Long slbClusterId) {
		this.slbClusterId = slbClusterId;
	}
	public String getContainerUuid() {
		return containerUuid;
	}
	public void setContainerUuid(String containerUuid) {
		this.containerUuid = containerUuid;
	}
}
