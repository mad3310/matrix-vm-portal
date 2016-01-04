package com.letv.lcp.openstack.model.compute;

import java.util.List;

import com.letv.lcp.openstack.model.base.Resource;
import com.letv.lcp.openstack.model.billing.BillingResource;
import com.letv.lcp.openstack.model.image.ImageResource;
import com.letv.lcp.openstack.model.network.IPAddresses;
import com.letv.lcp.openstack.model.storage.VolumeResource;


public interface VMResource extends Resource,BillingResource{
	public String getName();
	public String getAccessIPv4();
	public String getAccessIPv6();
	public String getStatus();
	String getTaskState();
	Integer getPowerState();
	String getVmState();
	ImageResource getImage();
	FlavorResource getFlavor();
//	NetworkResource getNetwork();
	IPAddresses getIpAddresses();
	String getAvailabilityZone();
	String getConfigDrive();
	Long getCreated();
	Long getUpdated();
	String getDiskConfig();
	String getHostId();
	String getKeyName();
	String getRegionDisplayName();
	List<VolumeResource> getVolumes();
}
