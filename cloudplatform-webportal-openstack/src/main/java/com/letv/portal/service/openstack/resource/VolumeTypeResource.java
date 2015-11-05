package com.letv.portal.service.openstack.resource;

public interface VolumeTypeResource extends Resource{
	String getName();
	Long getThroughput();
	Long getIops();
	Boolean getEnable();
	Long getCapacity();
}
