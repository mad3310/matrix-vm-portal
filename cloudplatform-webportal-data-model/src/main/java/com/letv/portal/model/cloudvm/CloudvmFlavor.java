package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public class CloudvmFlavor extends BaseModel {

	private static final long serialVersionUID = -2079817238965628633L;

	private String flavorId;
	private int vcpus;
	private int ram;
	private int disk;

	public CloudvmFlavor() {
	}
	
	public CloudvmFlavor(String flavorId, int vcpus, int ram, int disk) {
		this();
		this.flavorId = flavorId;
		this.vcpus = vcpus;
		this.ram = ram;
		this.disk = disk;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public int getVcpus() {
		return vcpus;
	}

	public void setVcpus(int vcpus) {
		this.vcpus = vcpus;
	}

	public int getRam() {
		return ram;
	}

	public void setRam(int ram) {
		this.ram = ram;
	}

	public int getDisk() {
		return disk;
	}

	public void setDisk(int disk) {
		this.disk = disk;
	}

}
