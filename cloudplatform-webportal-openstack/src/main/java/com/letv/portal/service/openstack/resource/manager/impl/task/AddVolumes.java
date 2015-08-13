package com.letv.portal.service.openstack.resource.manager.impl.task;

import java.util.List;

import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.Server;
import org.jclouds.openstack.nova.v2_0.extensions.VolumeAttachmentApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.io.Closeables;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.resource.manager.impl.VMManagerImpl;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;

public class AddVolumes implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(AddVolumes.class);

	private VMManagerImpl vmManager;
	private VolumeManagerImpl volumeManagerImpl;
	private String region;
	private Server server;
	private List<Volume> volumes;

	public AddVolumes(VMManagerImpl vmManager,
			VolumeManagerImpl volumeManagerImpl, String region, Server server,
			List<Volume> volumes) {
		this.vmManager = vmManager;
		this.volumeManagerImpl = volumeManagerImpl;
		this.region = region;
		this.server = server;
		this.volumes = volumes;
	}

	@Override
	public void run() {
		try {
			CinderApi cinderApi = volumeManagerImpl.openApi();
			try {
				NovaApi novaApi = vmManager.openApi();
				try {
					VolumeApi volumeApi = cinderApi.getVolumeApi(region);
					Optional<VolumeAttachmentApi> volumeAttachmentApiOptional = novaApi
							.getVolumeAttachmentApi(region);
					if (!volumeAttachmentApiOptional.isPresent()) {
						throw new APINotAvailableException(
								VolumeAttachmentApi.class);
					}
					VolumeAttachmentApi volumeAttachmentApi = volumeAttachmentApiOptional
							.get();
					for (Volume volume : volumes) {
						try {
							while (true) {
								volume = volumeApi.get(volume.getId());
								if (volume.getStatus() == Volume.Status.CREATING) {
									Thread.sleep(1000);
								} else {
									if (volume.getStatus() == Volume.Status.AVAILABLE) {
										volumeAttachmentApi
												.attachVolumeToServerAsDevice(
														volume.getId(),
														server.getId(), "");
									}
									break;
								}
							}
						} catch (InterruptedException e) {
							throw new PollingInterruptedException(e);
						}
					}
				} finally {
					Closeables.close(novaApi, false);
				}
			} finally {
				Closeables.close(cinderApi, false);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
