package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

public class VMCreateConf2 {
	private String region;

	private String name;

	private String flavorId;
	
	private String imageId;
	private String snapshotId;

	private int volumeSize;
	private String volumeTypeId;

	private String privateSubnetId;
	private String sharedNetworkId;
	
	private boolean bindFloatingIP;
	private int bandWidth;

	private String keyPairName;
	private String adminPass;

	private int count;

	public VMCreateConf2() {
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getSnapshotId() {
		return snapshotId;
	}

	public void setSnapshotId(String snapshotId) {
		this.snapshotId = snapshotId;
	}

	public String getFlavorId() {
		return flavorId;
	}

	public void setFlavorId(String flavorId) {
		this.flavorId = flavorId;
	}

	public int getVolumeSize() {
		return volumeSize;
	}

	public void setVolumeSize(int volumeSize) {
		this.volumeSize = volumeSize;
	}

	public String getVolumeTypeId() {
		return volumeTypeId;
	}

	public void setVolumeTypeId(String volumeTypeId) {
		this.volumeTypeId = volumeTypeId;
	}

	public boolean getBindFloatingIP() {
		return bindFloatingIP;
	}

	public void setBindFloatingIP(boolean bindFloatingIP) {
		this.bindFloatingIP = bindFloatingIP;
	}

	public int getBandWidth() {
		return bandWidth;
	}

	public void setBandWidth(int bandWidth) {
		this.bandWidth = bandWidth;
	}

	public String getPrivateSubnetId() {
		return privateSubnetId;
	}
	
	public void setPrivateSubnetId(String privateSubnetId) {
		this.privateSubnetId = privateSubnetId;
	}

	public String getSharedNetworkId() {
		return sharedNetworkId;
	}

	public void setSharedNetworkId(String sharedNetworkId) {
		this.sharedNetworkId = sharedNetworkId;
	}

	public String getKeyPairName() {
		return keyPairName;
	}

	public void setKeyPairName(String keyPairName) {
		this.keyPairName = keyPairName;
	}

	public String getAdminPass() {
		return adminPass;
	}

	public void setAdminPass(String adminPass) {
		this.adminPass = adminPass;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
