package com.letv.portal.service.openstack.resource;

public interface ImageResource extends Resource{
	public String getName();
	String getChecksum();
	String getContainerFormat();
	long getCreatedAt();
	long getUpdatedAt();
	long getDeletedAt();
	String getDiskFormat();
	String getLocation();
	long getMinDisk();
	long getMinRam();
	String getOwner();
	long getSize();
	String getStatus();
}
