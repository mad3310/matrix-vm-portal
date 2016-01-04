package com.letv.lcp.openstack.model.storage.impl;

import org.jclouds.openstack.cinder.v1.domain.VolumeAttachment;

import com.letv.lcp.openstack.model.storage.VolumeAttachmentResource;

public class VolumeAttachmentResourceImpl implements VolumeAttachmentResource {

	private VolumeAttachment volumeAttachment;
	private String vmName;

	public VolumeAttachmentResourceImpl(VolumeAttachment volumeAttachment) {
		this.volumeAttachment = volumeAttachment;
	}

	@Override
	public String getId() {
		return volumeAttachment.getId();
	}

	@Override
	public String getVolumeId() {
		return volumeAttachment.getVolumeId();
	}

	@Override
	public String getVmId() {
		return volumeAttachment.getServerId();
	}

	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	@Override
	public String getVmName() {
		return vmName;
	}

	@Override
	public String getDevice() {
		return volumeAttachment.getDevice();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((volumeAttachment == null) ? 0 : volumeAttachment.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof VolumeAttachmentResourceImpl)) {
			return false;
		}
		VolumeAttachmentResourceImpl other = (VolumeAttachmentResourceImpl) obj;
		if (volumeAttachment == null) {
			if (other.volumeAttachment != null) {
				return false;
			}
		} else if (!volumeAttachment.equals(other.volumeAttachment)) {
			return false;
		}
		return true;
	}

}
