package com.letv.portal.model;


/**Program Name: ContainerModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class ContainerModel extends BaseModel {
	
	private static final long serialVersionUID = 4029730587083735122L;
	
	private String id;   //主键ID
	private String nodeName; //节点名称
	private String nodeIp; //节点ip
	private String clusterId; //所属cluster
	private String hostId;  //所属host
	private Integer diskSize; //磁盘大小
	private Integer coresNumber; //cpu内核数
	private Integer cpuSpeed; //cpu速度 
	private Integer memorySize; //内存大小
	
	private String status; //状态:0:停止  1:正常
	private String isDeleted; //是否删除   0:无效 1:有效
	private String createTime;
	private String createUser;
	private String updateTime;
	private String updateUser;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeIp() {
		return nodeIp;
	}
	public void setNodeIp(String nodeIp) {
		this.nodeIp = nodeIp;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	public String getHostId() {
		return hostId;
	}
	public void setHostId(String hostId) {
		this.hostId = hostId;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	@Override
	public String toString() {
		return "ContainerModel [id=" + id + ", nodeName=" + nodeName
				+ ", nodeIp=" + nodeIp + ", clusterId=" + clusterId
				+ ", hostId=" + hostId + ", diskSize=" + diskSize
				+ ", coresNumber=" + coresNumber + ", cpuSpeed=" + cpuSpeed
				+ ", memorySize=" + memorySize + ", status=" + status
				+ ", isDeleted=" + isDeleted + ", createTime=" + createTime
				+ ", createUser=" + createUser + ", updateTime=" + updateTime
				+ ", updateUser=" + updateUser + "]";
	}
	
	
}
