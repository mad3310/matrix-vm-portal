package com.letv.lcp.cloudvm.model.task;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

public class VmCreateForm {
	
	private String groupId;
	private String imageId;
	private String clusterId;
	private int count;
	private Integer orderTime;
	private String applyUserEmail;
	private String operateUserEmail;
	private String callbackId;//申请组id（回调注册信息时使用）
	
	private String cpu;
	private String ram;
	private String disk;
	private String imageName;
	
	
	
	public String getCpu() {
		return cpu;
	}

	public void setCpu(String cpu) {
		this.cpu = cpu;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getDisk() {
		return disk;
	}

	public void setDisk(String disk) {
		this.disk = disk;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	@NotBlank
	public String getCallbackId() {
		return callbackId;
	}

	public void setCallbackId(String callbackId) {
		this.callbackId = callbackId;
	}

	//@NotBlank
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	@NotBlank
	public String getClusterId() {
		return clusterId;
	}

	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	@NotBlank
	public String getApplyUserEmail() {
		return applyUserEmail;
	}

	public void setApplyUserEmail(String applyUserEmail) {
		this.applyUserEmail = applyUserEmail;
	}
	@NotBlank
	public String getOperateUserEmail() {
		return operateUserEmail;
	}

	public void setOperateUserEmail(String operateUserEmail) {
		this.operateUserEmail = operateUserEmail;
	}

	//@NotBlank
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

    @Min(1)
    @Max(20)
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	@NotNull
	public Integer getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Integer orderTime) {
		this.orderTime = orderTime;
	}
	
}
