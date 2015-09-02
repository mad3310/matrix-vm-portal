package com.letv.portal.service.openstack.resource;

import java.util.List;

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
