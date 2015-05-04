package com.letv.portal.model.log;

import com.letv.common.model.BaseModel;
import com.letv.portal.enumeration.ServiceType;
import com.letv.portal.model.HclusterModel;
import com.letv.portal.model.HostModel;

public class LogContainer extends BaseModel {
	
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
	private String bindContainerPort;//容器内部服务端口
	private String bingHostPort;//物理机映射端口
	private String bingHostIp;//物理机映射IP
	private String bingProtocol;//
	private String mgrBindHostPort;//物理机映射端口
	private String containerUuid;//调用container用的UUID
	
	private ServiceType serviceType;
	
	private HostModel host;
	private LogCluster logCluster;
	private HclusterModel hcluster;
	
	private String hostIp; //ip
	private Long hostId;  //所属host
	private Long logClusterId; //所属cluster
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
	public String getBindContainerPort() {
		return bindContainerPort;
	}
	public void setBindContainerPort(String bindContainerPort) {
		this.bindContainerPort = bindContainerPort;
	}
	public String getBingHostPort() {
		return bingHostPort;
	}
	public void setBingHostPort(String bingHostPort) {
		this.bingHostPort = bingHostPort;
	}
	public String getBingHostIp() {
		return bingHostIp;
	}
	public void setBingHostIp(String bingHostIp) {
		this.bingHostIp = bingHostIp;
	}
	public String getBingProtocol() {
		return bingProtocol;
	}
	public void setBingProtocol(String bingProtocol) {
		this.bingProtocol = bingProtocol;
	}
	public String getMgrBindHostPort() {
		return mgrBindHostPort;
	}
	public void setMgrBindHostPort(String mgrBindHostPort) {
		this.mgrBindHostPort = mgrBindHostPort;
	}
	public String getContainerUuid() {
		return containerUuid;
	}
	public void setContainerUuid(String containerUuid) {
		this.containerUuid = containerUuid;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	public HostModel getHost() {
		return host;
	}
	public void setHost(HostModel host) {
		this.host = host;
	}
	public LogCluster getLogCluster() {
		return logCluster;
	}
	public void setLogCluster(LogCluster logCluster) {
		this.logCluster = logCluster;
	}
	public HclusterModel getHcluster() {
		return hcluster;
	}
	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
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
	public Long getLogClusterId() {
		return logClusterId;
	}
	public void setLogClusterId(Long logClusterId) {
		this.logClusterId = logClusterId;
	}
	
}
