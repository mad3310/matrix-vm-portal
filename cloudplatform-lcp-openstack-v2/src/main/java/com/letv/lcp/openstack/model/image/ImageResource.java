package com.letv.lcp.openstack.model.image;

import com.letv.lcp.openstack.model.base.Resource;

public interface ImageResource extends Resource{
	public String getName();
	String getChecksum();
	String getContainerFormat();
	Long getCreatedAt();
	Long getUpdatedAt();
	Long getDeletedAt();
	String getDiskFormat();
	String getLocation();
	Long getMinDisk();
	Long getMinRam();
	String getOwner();
	Long getSize();
	String getStatus();
	String getType();
	String getPlatform();
	String getVmName();
}
