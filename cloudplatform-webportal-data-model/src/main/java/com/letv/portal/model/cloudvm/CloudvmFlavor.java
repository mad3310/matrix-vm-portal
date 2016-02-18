package com.letv.portal.model.cloudvm;

import com.letv.common.model.BaseModel;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public class CloudvmFlavor extends BaseModel {

    private static final long serialVersionUID = -2079817238965628633L;

    private String region;
    private String flavorId;
    private Integer vcpus;
    private Integer ram;
    private Integer disk;
    private Integer storage;

    public CloudvmFlavor() {
    }

    public CloudvmFlavor(String region, String flavorId, Integer vcpus, Integer ram, Integer disk) {
        this();
        this.region = region;
        this.flavorId = flavorId;
        this.vcpus = vcpus;
        this.ram = ram;
        this.disk = disk;
    }

    
    public Integer getStorage() {
		return storage;
	}

	public void setStorage(Integer storage) {
		this.storage = storage;
	}

	public String getFlavorId() {
        return flavorId;
    }

    public void setFlavorId(String flavorId) {
        this.flavorId = flavorId;
    }

    public Integer getVcpus() {
        return vcpus;
    }

    public void setVcpus(Integer vcpus) {
        this.vcpus = vcpus;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getDisk() {
        return disk;
    }

    public void setDisk(Integer disk) {
        this.disk = disk;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getRegion() {
        return region;
    }
}
