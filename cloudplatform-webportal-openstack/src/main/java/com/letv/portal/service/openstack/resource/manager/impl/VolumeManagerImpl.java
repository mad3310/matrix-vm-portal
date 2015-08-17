package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.letv.portal.service.openstack.exception.*;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.Volume.Status;
import org.jclouds.openstack.cinder.v1.domain.VolumeAttachment;
import org.jclouds.openstack.cinder.v1.domain.VolumeQuota;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.impl.VolumeResourceImpl;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;

public class VolumeManagerImpl extends AbstractResourceManager<CinderApi> implements
		VolumeManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 378518940119989827L;
//	private CinderApi cinderApi;

	// private IdentityManagerImpl identityManager;

	public VolumeManagerImpl() {
	}
	
	public VolumeManagerImpl(
			OpenStackConf openStackConf, OpenStackUser openStackUser) {
		super( openStackConf, openStackUser);

//		Iterable<Module> modules = ImmutableSet
//				.<Module> of(new SLF4JLoggingModule());
//
//		cinderApi = ContextBuilder
//				.newBuilder("openstack-cinder")
//				.endpoint(openStackConf.getPublicEndpoint())
//				.credentials(
//						openStackUser.getUserId() + ":"
//								+ openStackUser.getUserId(),
//						openStackUser.getPassword()).modules(modules)
//				.buildApi(CinderApi.class);
	}

	@Override
	public Set<String> getRegions() throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, Set<String>>() {

			@Override
			public Set<String> run(CinderApi api) throws Exception {
				return api.getConfiguredRegions();
			}
		});
	}

	@Override
	public void close() throws IOException {
//		cinderApi.close();
	}

	@Override
	public VolumeResource get(final String region,final String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, VolumeResource>() {

			@Override
			public VolumeResource run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
				Volume volume = volumeApi.get(id);
				if (volume != null) {
					return new VolumeResourceImpl(region,
							VolumeManagerImpl.this.getRegionDisplayName(region), volume);
				} else {
					throw new VolumeNotFoundException(id);
				}
			}
		});
	}

//	public CinderApi getCinderApi() {
//		return cinderApi;
//	}

	public List<VolumeResource> getOfVM(final String region,
			final String regionDisplayName,final String vmId) throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, List<VolumeResource>>() {

			@Override
			public List<VolumeResource> run(CinderApi cinderApi) throws Exception {
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
		});
	}

	/**
	 * 
	 * @param regions
	 * @param name
	 * @param currentPagePara
	 *            从1开始
	 * @param recordsPerPage
	 * @return
	 * @throws RegionNotFoundException
	 * @throws ResourceNotFoundException
	 * @throws APINotAvailableException
	 * @throws OpenStackException
	 */
	private Page listByRegions(final Set<String> regions,final String name,
			final Integer currentPagePara,final Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, Page>() {

			@Override
			public Page run(CinderApi cinderApi) throws Exception {
				Integer currentPage = null;
				if (currentPagePara != null) {
					currentPage = currentPagePara - 1;
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
					page.setCurrentPage(currentPage + 1);
				} else {
					page.setCurrentPage(1);
				}
				return page;
			}
		});
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

	public List<Volume> create(final String region,final List<Integer> sizes)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, List<Volume>>() {

			@Override
			public List<Volume> run(CinderApi cinderApi) throws Exception {
				checkUserEmail();

				checkRegion(region);

				if (sizes.isEmpty()) {
					throw new UserOperationException(
							"The count of volume is less than or equal to zero.",
							"云硬盘的数量等于0");
				}
				int volumeCount = 0;
				int volumeTotalSize = 0;
				for (Integer size : sizes) {
					if (size <= 0) {
						throw new UserOperationException(
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
							.getByTenant(openStackUser.getTenantId());
					if (volumeQuota == null) {
						throw new OpenStackException("Volume quota is not available.",
								"云硬盘配额不可用。");
					}
					if (volumeTotalSize > volumeQuota.getGigabytes()) {
						throw new UserOperationException(
								"Volume size exceeding the quota.", "云硬盘大小超过配额。");
					}
					if (volumeCount > volumeQuota.getVolumes()) {
						throw new UserOperationException(
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
		});
	}

	@Override
	public void create(final String region,final int sizeGB,final String name,
			final String description,final Integer countPara) throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				

				checkUserEmail();

				checkRegion(region);
				if (sizeGB <= 0) {
					throw new UserOperationException(
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
				Integer count=countPara;
				if (count == null) {
					count = 1;
				}
				if (count <= 0) {
					throw new UserOperationException(
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
							.getByTenant(openStackUser.getTenantId());
					if (volumeQuota == null) {
						throw new OpenStackException("Volume quota is not available.",
								"云硬盘配额不可用。");
					}
					if (volumeTotalSize > volumeQuota.getGigabytes()) {
						throw new UserOperationException(
								"Volume size exceeding the quota.", "云硬盘大小超过配额。");
					}
					if (volumeCount > volumeQuota.getVolumes()) {
						throw new UserOperationException(
								"Volume count exceeding the quota.", "云硬盘数量超过配额。");
					}
				}

				for (int i = 0; i < count; i++) {
					volumeApi.create(sizeGB, createVolumeOptions);
				}
				
				return null;
			}
		
		});
	}

	@Override
	public void delete(final String region,final VolumeResource volumeResource)
			throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				Status status = ((VolumeResourceImpl) volumeResource).volume
						.getStatus();
				if (status != Status.AVAILABLE && status != Status.ERROR) {
					throw new UserOperationException("Volume is not removable.",
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
				
				return null;
			}
			
		});
	}

	public void waitingVolume(final String region,final String volumeId, 
		 final	VolumeChecker checker) throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				try {
					VolumeApi volumeApi=cinderApi.getVolumeApi(region);
					Volume volume = null;
					while (true) {
						volume = volumeApi.get(volumeId);
						if (checker.check(volume)) {
							break;
						}
						Thread.sleep(1000);
					}
				} catch (InterruptedException e) {
					throw new PollingInterruptedException(e);
				}
				
				return null;
			}
		});
	}

	public void waitingVolumeCreated(final String region, final String volumeId)
			throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {

				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
				try {
					while (true) {
						Volume volume = volumeApi.get(volumeId);
						if (volume.getStatus() == Volume.Status.CREATING) {
							Thread.sleep(1000);
						} else {
							break;
						}
					}
				} catch (InterruptedException e) {
					throw new PollingInterruptedException(e);
				}
				return null;
			}

		});
	}
	
	public void delete(final String region,final List<Volume> volumes) throws OpenStackException{
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				VolumeApi volumeApi = cinderApi
						.getVolumeApi(region);
				for (Volume volume : volumes) {
					// volumeManager.waitingVolumeCreated(region,
					// volume.getId());
					volumeApi.delete(volume.getId());
				}
				return null;
			}
		});
	}

	@Override
	protected String getProviderOrApi() {
		return "openstack-cinder";
	}

	@Override
	protected Class<CinderApi> getApiClass() {
		return CinderApi.class;
	}

	// public void setIdentityManager(IdentityManagerImpl identityManager) {
	// this.identityManager = identityManager;
	// }

}
