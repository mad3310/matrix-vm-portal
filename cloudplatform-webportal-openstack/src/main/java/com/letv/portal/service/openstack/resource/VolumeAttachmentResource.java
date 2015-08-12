package com.letv.portal.service.openstack.resource;

public interface VolumeAttachmentResource {
	String getId();
	String getVolumeId();
	String getVmId();
	String getDevice();
}
