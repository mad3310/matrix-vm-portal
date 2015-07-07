package com.letv.portal.service.openstack.resource.manager.impl.task;

import java.util.List;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private NovaApi novaApi;
	
	public AddVolumes(VMManagerImpl vmManager,
			VolumeManagerImpl volumeManagerImpl, String region, Server server,
			List<Integer> volumeSizes, NovaApi novaApi) {
		this.vmManager = vmManager;
		this.volumeManagerImpl = volumeManagerImpl;
		this.region = region;
		this.server = server;
		this.volumeSizes = volumeSizes;
		this.novaApi = novaApi;
	}

	@Override
	public void run() {
		try {
			VolumeApi volumeApi = volumeManagerImpl.getCinderApi()
					.getVolumeApi(region);
//			novaApi.getVolumeAttachmentApi(region).get().attachVolumeToServerAsDevice(volumeId, serverId, device)

			for (Integer size : volumeSizes) {
				Volume volume = volumeApi
						.create(size, CreateVolumeOptions.NONE);
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
