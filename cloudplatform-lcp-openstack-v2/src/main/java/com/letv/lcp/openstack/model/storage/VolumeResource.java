package com.letv.lcp.openstack.model.storage;

import java.util.List;
import java.util.Set;

import com.letv.lcp.openstack.model.base.Resource;
import com.letv.lcp.openstack.model.billing.BillingResource;

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
