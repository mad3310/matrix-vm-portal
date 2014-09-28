package com.letv.portal.model;


/**Program Name: HostModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class HostModel extends BaseModel {
	
	private static final long serialVersionUID = 3497965985607790962L;
	
	private String hostName; //主机名称
	private String hostIp; //主机ip
	private Integer nodesNumber; //节点个数
	private String hostModel; //主机型号
	private String cpuModel; //cpu型号
	private Integer coresNumber; //cpu核数
	private Integer memorySize; //内存大小
	private Integer diskSize; //磁盘大小
	private Integer diskUsed; //磁盘使用量

	private String status; //状态:0:关闭   1:正常
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getHostIp() {
		return hostIp;
	}
	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	public Integer getNodesNumber() {
		return nodesNumber;
	}
	public void setNodesNumber(Integer nodesNumber) {
		this.nodesNumber = nodesNumber;
	}
	public String getHostModel() {
		return hostModel;
	}
	public void setHostModel(String hostModel) {
		this.hostModel = hostModel;
	}
	
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	public Integer getCoresNumber() {
		return coresNumber;
	}
	public void setCoresNumber(Integer coresNumber) {
		this.coresNumber = coresNumber;
	}
	public Integer getMemorySize() {
		return memorySize;
	}
	public void setMemorySize(Integer memorySize) {
		this.memorySize = memorySize;
	}
	public Integer getDiskSize() {
		return diskSize;
	}
	public void setDiskSize(Integer diskSize) {
		this.diskSize = diskSize;
	}
	public Integer getDiskUsed() {
		return diskUsed;
	}
	public void setDiskUsed(Integer diskUsed) {
		this.diskUsed = diskUsed;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "HostModel [hostName=" + hostName + ", hostIp="
				+ hostIp + ", nodesNumber=" + nodesNumber + ", hostModel="
				+ hostModel + ", cpuModel=" + cpuModel + ", coresNumber="
				+ coresNumber + ", memorySize=" + memorySize + ", diskSize="
				+ diskSize + ", diskUsed=" + diskUsed + ", status=" + status
				+ "]";
	}
	
	
	
	
	
}
