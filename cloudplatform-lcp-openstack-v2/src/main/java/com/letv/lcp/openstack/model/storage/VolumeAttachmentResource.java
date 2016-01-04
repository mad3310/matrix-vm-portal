package com.letv.lcp.openstack.model.storage;

public interface VolumeAttachmentResource {
	String getId();
	String getVolumeId();
	String getVmId();
	String getVmName();
	String getDevice();
}
