package com.letv.lcp.openstack.model.storage;

import com.letv.lcp.openstack.model.base.Resource;

public interface VolumeTypeResource extends Resource{
	String getName();
	Long getThroughput();
	Long getIops();
	Boolean getEnable();
	Long getCapacity();
}
