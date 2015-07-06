package com.letv.portal.service.openstack.resource.impl;

import java.util.HashSet;
import java.util.Set;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.VolumeAttachment;

import com.letv.portal.service.openstack.resource.VolumeAttachmentResource;
import com.letv.portal.service.openstack.resource.VolumeResource;

public class VolumeResourceImpl extends AbstractResource implements
		VolumeResource {

	private String region;
	private Volume volume;
	private Set<VolumeAttachmentResource> attachments;

	public VolumeResourceImpl(String region, Volume volume) {
		this.region = region;
		this.volume = volume;
		this.attachments = new HashSet<VolumeAttachmentResource>();
		for (VolumeAttachment volumeAttachment : volume.getAttachments()) {
			this.attachments.add(new VolumeAttachmentResourceImpl(
					volumeAttachment));
		}
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return volume.getId();
	}

	@Override
	public String getName() {
		return volume.getName();
	}

	@Override
	public String getStatus() {
		return volume.getStatus().toString();
	}

	@Override
	public int getSize() {
		return volume.getSize();
	}

	@Override
	public String getZone() {
		return volume.getZone();
	}

	@Override
	public Long getCreated() {
		return volume.getCreated().getTime();
	}

	@Override
	public String getVolumeType() {
		return volume.getVolumeType();
	}

	@Override
	public String getSnapshotId() {
		return volume.getSnapshotId();
	}

	@Override
	public String getDescription() {
		return volume.getDescription();
	}

	@Override
	public String getTenantId() {
		return volume.getTenantId();
	}

	@Override
	public Set<VolumeAttachmentResource> getAttachments() {
		return attachments;
	}

}
