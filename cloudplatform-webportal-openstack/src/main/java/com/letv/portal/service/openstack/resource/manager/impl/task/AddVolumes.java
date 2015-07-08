package com.letv.portal.service.openstack.resource.manager.impl.task;

import java.util.List;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;

public class AddVolumes implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(AddVolumes.class);

	private VMManagerImpl vmManager;
	private VolumeManagerImpl volumeManagerImpl;
	private String region;
	private Server server;
	private List<Integer> volumeSizes;

	public AddVolumes(VMManagerImpl vmManager,
			VolumeManagerImpl volumeManagerImpl, String region, Server server,
			List<Integer> volumeSizes) {
		this.vmManager = vmManager;
		this.volumeManagerImpl = volumeManagerImpl;
		this.region = region;
		this.server = server;
		this.volumeSizes = volumeSizes;
	}

	@Override
	public void run() {
		try {
			VolumeApi volumeApi = volumeManagerImpl.getCinderApi()
					.getVolumeApi(region);
			Optional<VolumeAttachmentApi> volumeAttachmentApiOptional = vmManager
					.getNovaApi().getVolumeAttachmentApi(region);
			if (!volumeAttachmentApiOptional.isPresent()) {
				throw new APINotAvailableException(VolumeAttachmentApi.class);
			}
			VolumeAttachmentApi volumeAttachmentApi = volumeAttachmentApiOptional
					.get();
			for (Integer size : volumeSizes) {
				Volume volume = volumeApi
						.create(size, CreateVolumeOptions.NONE);
				volumeAttachmentApi.attachVolumeToServerAsDevice(
						volume.getId(), server.getId(), "/dev/vdc");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
