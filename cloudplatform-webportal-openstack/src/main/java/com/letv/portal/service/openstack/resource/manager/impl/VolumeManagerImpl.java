package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jclouds.ContextBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.VolumeQuota;
import org.jclouds.openstack.cinder.v1.domain.Volume.Status;
import org.jclouds.openstack.cinder.v1.domain.VolumeAttachment;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;

import com.google.common.collect.ImmutableSet;
import com.google.inject.Module;
import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.impl.VolumeResourceImpl;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;

public class VolumeManagerImpl extends AbstractResourceManager implements
		VolumeManager {

	private CinderApi cinderApi;

	public VolumeManagerImpl(OpenStackServiceGroup openStackServiceGroup,
			OpenStackConf openStackConf, OpenStackUser openStackUser) {
		super(openStackServiceGroup, openStackConf, openStackUser);

		Iterable<Module> modules = ImmutableSet
				.<Module> of(new SLF4JLoggingModule());

		cinderApi = ContextBuilder
				.newBuilder("openstack-cinder")
				.endpoint(openStackConf.getPublicEndpoint())
				.credentials(
						openStackUser.getUserId() + ":"
								+ openStackUser.getUserId(),
						openStackUser.getPassword()).modules(modules)
				.buildApi(CinderApi.class);
	}

	@Override
	public Set<String> getRegions() {
		return cinderApi.getConfiguredRegions();
	}

	@Override
	public void close() throws IOException {
		cinderApi.close();
	}

	@Override
	public VolumeResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		checkRegion(region);

		VolumeApi volumeApi = cinderApi.getVolumeApi(region);
		Volume volume = volumeApi.get(id);
		if (volume != null) {
			return new VolumeResourceImpl(region,
					this.getRegionDisplayName(region), volume);
		} else {
			throw new ResourceNotFoundException("Volume", "卷", id);
		}
	}

	public CinderApi getCinderApi() {
		return cinderApi;
	}

	public List<VolumeResource> getOfVM(String region,
			String regionDisplayName, String vmId) {
		VolumeApi volumeApi = cinderApi.getVolumeApi(region);
		List<? extends Volume> volumeList = volumeApi.listInDetail().toList();
		List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
		for (Volume volume : volumeList) {
			for (VolumeAttachment volumeAttachment : volume.getAttachments()) {
				if (vmId.equals(volumeAttachment.getServerId())) {
					volumeResources.add(new VolumeResourceImpl(region,
							regionDisplayName, volume));
					break;
				}
			}
		}
		return volumeResources;
	}

	/**
	 * 
	 * @param regions
	 * @param name
	 * @param currentPage
	 *            从1开始
	 * @param recordsPerPage
	 * @return
	 * @throws RegionNotFoundException
	 * @throws ResourceNotFoundException
	 * @throws APINotAvailableException
	 * @throws OpenStackException
	 */
	private Page listByRegions(Set<String> regions, String name,
			Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		if (currentPage != null) {
			currentPage -= 1;
		}

		Map<String, String> transMap = getRegionCodeToDisplayNameMap();
		List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
		int volumeCount = 0;
		boolean needCollect = true;
		for (String region : regions) {
			VolumeApi volumeApi = cinderApi.getVolumeApi(region);
			if (needCollect) {
				String regionDisplayName = transMap.get(region);
				List<? extends Volume> volumes = volumeApi.listInDetail()
						.toList();
				for (Volume volume : volumes) {
					if (name == null
							|| (volume.getName() != null && volume.getName()
									.contains(name))) {
						if (currentPage == null || recordsPerPage == null) {
							volumeResources.add(new VolumeResourceImpl(region,
									regionDisplayName, volume));
						} else {
							if (needCollect) {
								if (volumeCount >= (currentPage + 1)
										* recordsPerPage) {
									needCollect = false;
								} else if (volumeCount >= currentPage
										* recordsPerPage) {
									volumeResources.add(new VolumeResourceImpl(
											region, regionDisplayName, volume));
								}
							}
						}
						volumeCount++;
					}
				}
			} else {
				for (Volume volume : volumeApi.list().toList()) {
					if (name == null
							|| (volume.getName() != null && volume.getName()
									.contains(name))) {
						volumeCount++;
					}
				}
			}
		}

		Page page = new Page();
		page.setData(volumeResources);
		page.setTotalRecords(volumeCount);
		if (recordsPerPage != null) {
			page.setRecordsPerPage(recordsPerPage);
		} else {
			page.setRecordsPerPage(10);
		}
		if (currentPage != null) {
			page.setCurrentPage(currentPage);
		} else {
			page.setCurrentPage(1);
		}
		return page;
	}

	@Override
	public Page listAll(String name, Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		return listByRegions(getRegions(), name, currentPage, recordsPerPage);
	}

	@Override
	public Page listByRegionGroup(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		return listByRegions(getGroupRegions(regionGroup), name, currentPage,
				recordsPerPage);
	}

	public List<Volume> create(String region, List<Integer> sizes)
			throws OpenStackException {
		checkRegion(region);

		if (sizes.isEmpty()) {
			throw new OpenStackException(
					"The count of volume is less than or equal to zero.",
					"云硬盘的数量等于0");
		}
		int volumeCount = 0;
		int volumeTotalSize = 0;
		for (Integer size : sizes) {
			if (size <= 0) {
				throw new OpenStackException(
						"The size of the cloud disk can not be less than or equal to zero.",
						"云硬盘的大小不能小于或等于零。");
			}
			volumeCount++;
			volumeTotalSize += size;
		}

		VolumeApi volumeApi = cinderApi.getVolumeApi(region);

		{
			List<? extends Volume> volumes = volumeApi.list().toList();
			for (Volume volume : volumes) {
				volumeCount++;
				volumeTotalSize += volume.getSize();
			}

			VolumeQuota volumeQuota = cinderApi.getQuotaApi(region)
					.getByTenant(openStackUser.getUserId());
			if (volumeQuota == null) {
				throw new OpenStackException("Volume quota is not available.",
						"云硬盘配额不可用。");
			}
			if (volumeTotalSize > volumeQuota.getGigabytes()) {
				throw new OpenStackException(
						"Volume size exceeding the quota.", "云硬盘大小超过配额。");
			}
			if (volumeCount > volumeQuota.getVolumes()) {
				throw new OpenStackException(
						"Volume count exceeding the quota.", "云硬盘数量超过配额。");
			}
		}

		CreateVolumeOptions createVolumeOptions = new CreateVolumeOptions();
		List<Volume> volumeList = new LinkedList<Volume>();
		for (Integer size : sizes) {
			volumeList.add(volumeApi.create(size, createVolumeOptions));
		}
		return volumeList;
	}

	@Override
	public void create(String region, int sizeGB, String name,
			String description, Integer count) throws OpenStackException {
		checkRegion(region);
		if (sizeGB <= 0) {
			throw new OpenStackException(
					"The size of the cloud disk can not be less than or equal to zero.",
					"云硬盘的大小不能小于或等于零。");
		}

		VolumeApi volumeApi = cinderApi.getVolumeApi(region);
		CreateVolumeOptions createVolumeOptions = new CreateVolumeOptions();
		if (name != null) {
			createVolumeOptions.name(name);
		}
		if (description != null) {
			createVolumeOptions.description(description);
		}
		if (count == null) {
			count = 1;
		}
		if (count <= 0) {
			throw new OpenStackException(
					"The count of volume is less than or equal to zero.",
					"云硬盘的数量不能小于或等于0");
		}

		{
			List<? extends Volume> volumes = volumeApi.list().toList();
			int volumeCount = count;
			int volumeTotalSize = count * sizeGB;
			for (Volume volume : volumes) {
				volumeCount++;
				volumeTotalSize += volume.getSize();
			}

			VolumeQuota volumeQuota = cinderApi.getQuotaApi(region)
					.getByTenant(openStackUser.getUserId());
			if (volumeQuota == null) {
				throw new OpenStackException("Volume quota is not available.",
						"云硬盘配额不可用。");
			}
			if (volumeTotalSize > volumeQuota.getGigabytes()) {
				throw new OpenStackException(
						"Volume size exceeding the quota.", "云硬盘大小超过配额。");
			}
			if (volumeCount > volumeQuota.getVolumes()) {
				throw new OpenStackException(
						"Volume count exceeding the quota.", "云硬盘数量超过配额。");
			}
		}

		for (int i = 0; i < count; i++) {
			volumeApi.create(sizeGB, createVolumeOptions);
		}
	}

	@Override
	public void delete(String region, VolumeResource volumeResource)
			throws OpenStackException {
		checkRegion(region);

		Status status = ((VolumeResourceImpl) volumeResource).volume
				.getStatus();
		if (status != Status.AVAILABLE && status != Status.ERROR) {
			throw new OpenStackException("Volume is not removable.",
					"云硬盘不是可删除的状态。");
		}

		VolumeApi volumeApi = cinderApi.getVolumeApi(region);
		boolean isSuccess = volumeApi.delete(volumeResource.getId());
		if (!isSuccess) {
			throw new OpenStackException(MessageFormat.format(
					"Volume \"{0}\" delete failed.", volumeResource.getId()),
					MessageFormat.format("云硬盘“{0}”删除失败。",
							volumeResource.getId()));
		}
	}

}
