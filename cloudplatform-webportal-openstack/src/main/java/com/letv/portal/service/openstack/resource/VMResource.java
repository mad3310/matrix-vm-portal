package com.letv.portal.service.openstack.resource;

public interface VMResource extends Resource{
	public String getName();
	public String getAccessIPv4();
	public String getAccessIPv6();
	public String getStatus();
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
}
