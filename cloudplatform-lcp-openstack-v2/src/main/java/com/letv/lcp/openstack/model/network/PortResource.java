package com.letv.lcp.openstack.model.network;

import java.util.List;

import com.letv.lcp.openstack.model.base.Resource;

public interface PortResource extends Resource {
	String getRegionDisplayName();

	String getName();

	List<SubnetIp> getFixedIps();

	String getStatus();

	String getNetworkId();

	String getMacAddress();

	String getDeviceOwner();

	String getDeviceId();
}
