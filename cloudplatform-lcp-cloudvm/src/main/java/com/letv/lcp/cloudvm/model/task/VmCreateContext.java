package com.letv.lcp.cloudvm.model.task;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;


public class VmCreateContext {

	private String volumeInstanceId;
	private Long volumeDbId;
	
	private String floatingIpInstanceId;
	private Long floatingIpDbId;
	private String publicIp;	
	private String carrierName;
	
	private String subnetPortInstanceId;
	
	private Integer cpu;
	private Integer ram;
	private String privateIp;
	private String serverInstanceId;
	private Long serverDbId;
	
	private String resourceName;
	private Date floatingIpBindDate;
	
	@JSONField(serialize=false)
	public Integer getCpu() {
		return cpu;
	}
	public void setCpu(Integer cpu) {
		this.cpu = cpu;
	}
	@JSONField(serialize=false)
	public Integer getRam() {
		return ram;
	}
	public void setRam(Integer ram) {
		this.ram = ram;
	}
	@JSONField(serialize=false)
	public String getPrivateIp() {
		return privateIp;
	}
	public void setPrivateIp(String privateIp) {
		this.privateIp = privateIp;
	}
	@JSONField(serialize=false)
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	@JSONField(serialize=false)
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public Long getVolumeDbId() {
		return volumeDbId;
	}
	public void setVolumeDbId(Long volumeDbId) {
		this.volumeDbId = volumeDbId;
	}
	public Long getFloatingIpDbId() {
		return floatingIpDbId;
	}
	public void setFloatingIpDbId(Long floatingIpDbId) {
		this.floatingIpDbId = floatingIpDbId;
	}
	public Long getServerDbId() {
		return serverDbId;
	}
	public void setServerDbId(Long serverDbId) {
		this.serverDbId = serverDbId;
	}
	public Date getFloatingIpBindDate() {
		return floatingIpBindDate;
	}
	public void setFloatingIpBindDate(Date floatingIpBindDate) {
		this.floatingIpBindDate = floatingIpBindDate;
	}
	public String getVolumeInstanceId() {
		return volumeInstanceId;
	}
	public void setVolumeInstanceId(String volumeInstanceId) {
		this.volumeInstanceId = volumeInstanceId;
	}
	public String getFloatingIpInstanceId() {
		return floatingIpInstanceId;
	}
	public void setFloatingIpInstanceId(String floatingIpInstanceId) {
		this.floatingIpInstanceId = floatingIpInstanceId;
	}
	public String getSubnetPortInstanceId() {
		return subnetPortInstanceId;
	}
	public void setSubnetPortInstanceId(String subnetPortInstanceId) {
		this.subnetPortInstanceId = subnetPortInstanceId;
	}
	public String getServerInstanceId() {
		return serverInstanceId;
	}
	public void setServerInstanceId(String serverInstanceId) {
		this.serverInstanceId = serverInstanceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	

}
