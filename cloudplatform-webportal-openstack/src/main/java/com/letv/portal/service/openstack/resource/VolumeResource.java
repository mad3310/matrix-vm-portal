package com.letv.portal.service.openstack.resource;

import com.letv.portal.service.openstack.billing.BillingResource;

import java.util.List;
import java.util.Set;

public interface VolumeResource extends Resource, BillingResource {
	String getName();
	String getStatus();
	int getSize();
	String getZone();
	Long getCreated();
	String getVolumeType();
	String getSnapshotId();
	String getDescription();
//	String getTenantId();
	Set<VolumeAttachmentResource> getAttachments();
	String getRegionDisplayName();
	List<VolumeSnapshotResource> getSnapshots();
}
