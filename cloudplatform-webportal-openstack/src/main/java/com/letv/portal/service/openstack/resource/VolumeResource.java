package com.letv.portal.service.openstack.resource;

import java.util.Set;

public interface VolumeResource extends Resource {
	String getName();
	String getStatus();
	int getSize();
	String getZone();
	Long getCreated();
	String getVolumeType();
	String getSnapshotId();
	String getDescription();
	String getTenantId();
	Set<VolumeAttachmentResource> getAttachments();
	String getRegionDisplayName();
}
