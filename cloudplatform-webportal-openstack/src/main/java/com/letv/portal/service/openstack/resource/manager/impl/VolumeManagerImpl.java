package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;

import com.letv.portal.model.cloudvm.CloudvmRcCountType;
import com.letv.portal.model.cloudvm.CloudvmVolume;
import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.listeners.event.VolumeCreateFailEvent;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.local.service.LocalRcCountService;
import com.letv.portal.service.openstack.resource.manager.VolumeCreateConf;
import com.letv.portal.service.openstack.billing.listeners.VolumeCreateListener;
import com.letv.portal.service.openstack.billing.listeners.event.VolumeCreateEvent;
import com.letv.portal.service.openstack.exception.*;

import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.resource.VolumeAttachmentResource;
import com.letv.portal.service.openstack.resource.VolumeSnapshotResource;
import com.letv.portal.service.openstack.resource.impl.VolumeAttachmentResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VolumeSnapshotResourceImpl;
import com.letv.portal.service.openstack.util.ExceptionUtil;
import com.letv.portal.service.openstack.util.Ref;
import com.letv.portal.service.openstack.util.ThreadUtil;
import com.letv.portal.service.openstack.util.function.Function;
import org.apache.commons.lang3.StringUtils;
import org.jclouds.openstack.cinder.v1.CinderApi;
import org.jclouds.openstack.cinder.v1.domain.*;
import org.jclouds.openstack.cinder.v1.domain.Volume.Status;
import org.jclouds.openstack.cinder.v1.features.SnapshotApi;
import org.jclouds.openstack.cinder.v1.features.VolumeApi;
import org.jclouds.openstack.cinder.v1.features.VolumeTypeApi;
import org.jclouds.openstack.cinder.v1.options.CreateSnapshotOptions;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.impl.OpenStackConf;
import com.letv.portal.service.openstack.impl.OpenStackUser;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;
import com.letv.portal.service.openstack.resource.impl.VolumeResourceImpl;
import com.letv.portal.service.openstack.resource.impl.VolumeTypeResourceImpl;
import com.letv.portal.service.openstack.resource.manager.VolumeManager;
import org.jclouds.openstack.v2_0.domain.Resource;

public class VolumeManagerImpl extends AbstractResourceManager<CinderApi>
		implements VolumeManager {

	/**
	 *
	 */
	private static final long serialVersionUID = 378518940119989827L;

	private VMManagerImpl vmManager;

	// private CinderApi cinderApi;

	// private IdentityManagerImpl identityManager;

	public VolumeManagerImpl() {
	}

	public VolumeManagerImpl(OpenStackConf openStackConf,
			OpenStackUser openStackUser) {
		super(openStackConf, openStackUser);

		// Iterable<Module> modules = ImmutableSet
		// .<Module> of(new SLF4JLoggingModule());
		//
		// cinderApi = ContextBuilder
		// .newBuilder("openstack-cinder")
		// .endpoint(openStackConf.getPublicEndpoint())
		// .credentials(
		// openStackUser.getUserId() + ":"
		// + openStackUser.getUserId(),
		// openStackUser.getPassword()).modules(modules)
		// .buildApi(CinderApi.class);
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

	public void checkRegion(CinderApi cinderApi, String region) throws OpenStackException,RegionNotFoundException {
		if (!cinderApi.getConfiguredRegions().contains(region)) {
			throw new RegionNotFoundException(region);
		}
	}

	@Override
	public void close() throws IOException {
		// cinderApi.close();
	}

	public void checkVolumeOperational(long tenantId,String region,String volumeId) throws OpenStackException {
		CloudvmVolume cloudvmVolume=OpenStackServiceImpl.getOpenStackServiceGroup().getCloudvmVolumeService().selectByVolumeId(tenantId, region, volumeId);
		if(cloudvmVolume==null){
			throw new ResourceNotFoundException("Volume","云硬盘",volumeId);
		}else{
			if(cloudvmVolume.getStatus()== CloudvmVolumeStatus.WAITING_ATTACHING){
				throw new UserOperationException("volume.status==WAITING_ATTACHING","云硬盘正在挂载中，请稍后操作");
			}
		}
	}

	@Override
	public VolumeResource get(final String region, final String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, VolumeResource>() {

			@Override
			public VolumeResource run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
				Volume volume = volumeApi.get(id);
				if (volume != null) {
					return new VolumeResourceImpl(
							region,
							VolumeManagerImpl.this.getRegionDisplayName(region),
							volume);
				} else {
					throw new VolumeNotFoundException(id);
				}
			}
		});
	}

	// public CinderApi getCinderApi() {
	// return cinderApi;
	// }

	public List<VolumeResource> getOfVM(final String region,
			final String regionDisplayName, final String vmId)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, List<VolumeResource>>() {

			@Override
			public List<VolumeResource> run(CinderApi cinderApi)
					throws Exception {
				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
				List<? extends Volume> volumeList = volumeApi.listInDetail()
						.toList();
				List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
				for (Volume volume : volumeList) {
					for (VolumeAttachment volumeAttachment : volume
							.getAttachments()) {
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

    public Page list(final String region, final String name, final Integer currentPagePara, final Integer recordsPerPage) throws OpenStackException {
        return runWithApi(new ApiRunnable<CinderApi, Page>() {
            @Override
            public Page run(final CinderApi cinderApi) throws Exception {
                checkRegion(region);

                final Ref<Integer> currentPageRef = new Ref<Integer>();

                final List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();
                final Map<String, Resource> idToServer = new HashMap<String, Resource>();
                final Ref<Integer> volumeCountRef = new Ref<Integer>();

                ThreadUtil.concurrentRunAndWait(new Function<Void>() {
					@Override
					public Void apply() {
						try {
							List<Resource> servers = vmManager.listServer(region);
							for (Resource server : servers) {
								idToServer.put(server.getId(), server);
							}
						} catch (OpenStackException e) {
							throw e.matrixException();
						}
						return null;
					}
				}, new Function<Void>() {
					@Override
					public Void apply() {
						try {
							Integer currentPage = null;
							if (currentPagePara != null) {
								currentPage = currentPagePara - 1;
								currentPageRef.set(currentPage);
							}

							String regionDisplayName = getRegionDisplayName(region);

							List<? extends Volume> volumes = cinderApi.getVolumeApi(region).listInDetail().toList();
							int volumeCount = 0;
							for (Volume volume : volumes) {
								if (name == null
										|| (volume.getName() != null && volume
										.getName().contains(name))) {
									if (currentPage == null
											|| recordsPerPage == null) {
										volumeResources.add(new VolumeResourceImpl(
												region, regionDisplayName, volume));
									} else if (volumeCount < (currentPage + 1)
											* recordsPerPage && volumeCount >= currentPage
											* recordsPerPage) {
										volumeResources
												.add(new VolumeResourceImpl(
														region,
														regionDisplayName,
														volume));
									}
									volumeCount++;
								}
							}
							volumeCountRef.set(volumeCount);
						} catch (OpenStackException e) {
							throw e.matrixException();
						}
						return null;
					}
				});

                for (VolumeResource volumeResource : volumeResources) {
                    for (VolumeAttachmentResource volumeAttachmentResource : volumeResource.getAttachments()) {
                        String vmId = volumeAttachmentResource.getVmId();
                        if (vmId != null) {
							Resource server = idToServer.get(vmId);
                            if (server != null) {
                                String vmName = server.getName();
                                ((VolumeAttachmentResourceImpl) volumeAttachmentResource).setVmName(vmName);
                            }
                        }
                    }
                }

                Page page = new Page();
                page.setData(volumeResources);
                page.setTotalRecords(volumeCountRef.get());
                if (recordsPerPage != null) {
                    page.setRecordsPerPage(recordsPerPage);
                } else {
                    page.setRecordsPerPage(10);
                }
                if (currentPageRef.get() != null) {
                    page.setCurrentPage(currentPageRef.get() + 1);
                } else {
                    page.setCurrentPage(1);
                }
                return page;
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
	@Deprecated
	private Page listByRegions(final Set<String> regions, final String name,
			final Integer currentPagePara, final Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, Page>() {

			@Override
			public Page run(final CinderApi cinderApi) throws Exception {
				final Ref<Integer> currentPageRef = new Ref<Integer>();
				final Ref<Integer> volumeCountRef = new Ref<Integer>();

				final List<VolumeResource> volumeResources = new LinkedList<VolumeResource>();

				ThreadUtil.concurrentRunAndWait(new Function<Void>() {
					@Override
					public Void apply() {
						Integer currentPage = null;
						if (currentPagePara != null) {
							currentPage = currentPagePara - 1;
							currentPageRef.set(currentPage);
						}

						final Map<String, String> transMap = getRegionCodeToDisplayNameMap();

						int volumeCount = 0;
						boolean needCollect = true;
						for (String region : regions) {
							VolumeApi volumeApi = cinderApi.getVolumeApi(region);
							if (needCollect) {
								String regionDisplayName = transMap.get(region);
								List<? extends Volume> volumes = volumeApi
										.listInDetail().toList();
								for (Volume volume : volumes) {
									if (name == null
											|| (volume.getName() != null && volume
											.getName().contains(name))) {
										if (currentPage == null
												|| recordsPerPage == null) {
											volumeResources.add(new VolumeResourceImpl(
													region, regionDisplayName, volume));
										} else {
											if (needCollect) {
												if (volumeCount >= (currentPage + 1)
														* recordsPerPage) {
													needCollect = false;
												} else if (volumeCount >= currentPage
														* recordsPerPage) {
													volumeResources
															.add(new VolumeResourceImpl(
																	region,
																	regionDisplayName,
																	volume));
												}
											}
										}
										volumeCount++;
									}
								}
							} else {
								for (Volume volume : volumeApi.list().toList()) {
									if (name == null
											|| (volume.getName() != null && volume
											.getName().contains(name))) {
										volumeCount++;
									}
								}
							}
						}

						volumeCountRef.set(volumeCount);
						return null;
					}
				}, new Function<Void>() {
					@Override
					public Void apply() {
						return null;
					}
				});

				Page page = new Page();
				page.setData(volumeResources);
				page.setTotalRecords(volumeCountRef.get());
				if (recordsPerPage != null) {
					page.setRecordsPerPage(recordsPerPage);
				} else {
					page.setRecordsPerPage(10);
				}
				if (currentPageRef.get() != null) {
					page.setCurrentPage(currentPageRef.get() + 1);
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

	public List<Volume> create(final String region, final List<Integer> sizes)
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
						throw new OpenStackException(
								"Volume quota is not available.", "云硬盘配额不可用。");
					}
					if (volumeTotalSize > volumeQuota.getGigabytes()) {
						throw new UserOperationException(
								"Volume size exceeding the quota.",
								"云硬盘大小超过配额。");
					}
					if (volumeCount > volumeQuota.getVolumes()) {
						throw new UserOperationException(
								"Volume count exceeding the quota.",
								"云硬盘数量超过配额。");
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
	public void create(final VolumeCreateConf volumeCreateConf)
			throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				create(cinderApi,volumeCreateConf,null,null);
				return null;
			}

		});
	}

	@Override
	public void checkCreate(final VolumeCreateConf volumeCreateConf) throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {
			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				checkCreate(cinderApi, volumeCreateConf);
				return null;
			}
		});
	}

	@Override
	public VolumeTypeResource getVolumeTypeResource(final String region, final String volumeTypeId) throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, VolumeTypeResource>() {
			@Override
			public VolumeTypeResource run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				VolumeTypeApi volumeTypeApi = cinderApi.getVolumeTypeApi(region);
				VolumeType volumeType = volumeTypeApi.get(volumeTypeId);
				if (volumeType == null) {
					throw new ResourceNotFoundException("Volume Type", "云硬盘类型", volumeTypeId);
				}
				return new VolumeTypeResourceImpl(region, volumeType);
			}
		});
	}

	private void checkCreate(CinderApi cinderApi, VolumeCreateConf volumeCreateConf) throws OpenStackException {
		checkUserEmail();

		checkRegion(volumeCreateConf.getRegion());
		if (volumeCreateConf.getSize() <= 0) {
			throw new UserOperationException(
					"The size of the cloud disk can not be less than or equal to zero.",
					"云硬盘的大小不能小于或等于零。");
		}

		CreateVolumeOptions createVolumeOptions = new CreateVolumeOptions();

		if (StringUtils.isNotEmpty(volumeCreateConf.getVolumeTypeId())) {
			VolumeTypeApi volumeTypeApi = cinderApi.getVolumeTypeApi(volumeCreateConf.getRegion());
			VolumeType volumeType = volumeTypeApi.get(volumeCreateConf.getVolumeTypeId());
			if (volumeType == null) {
				throw new ResourceNotFoundException("Volume Type", "云硬盘类型", volumeCreateConf.getVolumeTypeId());
			}
			createVolumeOptions.volumeType(volumeType.getId());
		}

		if (volumeCreateConf.getName() != null) {
			createVolumeOptions.name(volumeCreateConf.getName());
		}
		if (volumeCreateConf.getDescription() != null) {
			createVolumeOptions.description(volumeCreateConf.getDescription());
		}
		if (volumeCreateConf.getVolumeSnapshotId() != null) {
			Snapshot snapshot = cinderApi.getSnapshotApi(volumeCreateConf.getRegion()).get(volumeCreateConf.getVolumeSnapshotId());
			if (snapshot == null) {
				throw new ResourceNotFoundException("Volume Snapshot", "云硬盘快照", volumeCreateConf.getVolumeSnapshotId());
			} else {
				if (snapshot.getSize() > volumeCreateConf.getSize()) {
					throw new UserOperationException("Volume size can not be less than volume snapshot size.", "云硬盘的大小不能小于云硬盘快照的大小");
				}
				createVolumeOptions.snapshotId(volumeCreateConf.getVolumeSnapshotId());
			}
		}
		Integer count = volumeCreateConf.getCount();
		if (count == null) {
			count = 1;
		}
		if (count <= 0) {
			throw new UserOperationException(
					"The count of volume is less than or equal to zero.",
					"云硬盘的数量不能小于或等于0");
		}

		VolumeApi volumeApi = cinderApi.getVolumeApi(volumeCreateConf.getRegion());

		{
			List<? extends Volume> volumes = volumeApi.list().toList();
			List<? extends Snapshot> snapshots = cinderApi.getSnapshotApi(volumeCreateConf.getRegion()).list().toList();
			int pureVolumeSize = 0;
			for (Volume volume : volumes) {
				pureVolumeSize += volume.getSize();
			}
			long userVoUserId=openStackUser.getUserVoUserId();
			String region=volumeCreateConf.getRegion();

			LocalCommonQuotaSerivce localCommonQuotaSerivce = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce();
			localCommonQuotaSerivce.checkQuota(userVoUserId, region, CommonQuotaType.CLOUDVM_VOLUME, volumes.size() + count);
			localCommonQuotaSerivce.checkQuota(userVoUserId, region, CommonQuotaType.CLOUDVM_VOLUME_SIZE, pureVolumeSize + count * volumeCreateConf.getSize());

			VolumeQuota volumeQuota = cinderApi.getQuotaApi(volumeCreateConf.getRegion())
					.getByTenant(openStackUser.getTenantId());
			if (volumeQuota == null) {
				throw new OpenStackException(
						"Volume quota is not available.", "云硬盘配额不可用。");
			}
			if (sumGigabytes(volumes, snapshots) + count * volumeCreateConf.getSize() > volumeQuota.getGigabytes()) {
				throw new UserOperationException(
						"Volume size exceeding the quota.",
						"云硬盘大小超过配额。");
			}
			if (volumes.size() + count > volumeQuota.getVolumes()) {
				throw new UserOperationException(
						"Volume count exceeding the quota.",
						"云硬盘数量超过配额。");
			}
		}
	}

	private void create(CinderApi cinderApi, VolumeCreateConf volumeCreateConf, VolumeCreateListener listener, Object listenerUserData, List<Volume> successCreatedVolumes) throws OpenStackException{
		checkUserEmail();

		checkRegion(cinderApi, volumeCreateConf.getRegion());
		if (volumeCreateConf.getSize() <= 0) {
			throw new UserOperationException(
					"The size of the cloud disk can not be less than or equal to zero.",
					"云硬盘的大小不能小于或等于零。");
		}

		CreateVolumeOptions createVolumeOptions = new CreateVolumeOptions();

		if (StringUtils.isNotEmpty(volumeCreateConf.getVolumeTypeId())) {
			VolumeTypeApi volumeTypeApi = cinderApi.getVolumeTypeApi(volumeCreateConf.getRegion());
			VolumeType volumeType = volumeTypeApi.get(volumeCreateConf.getVolumeTypeId());
			if (volumeType == null) {
				throw new ResourceNotFoundException("Volume Type", "云硬盘类型", volumeCreateConf.getVolumeTypeId());
			}
			createVolumeOptions.volumeType(volumeType.getId());
		}

		if (volumeCreateConf.getName() != null) {
			createVolumeOptions.name(volumeCreateConf.getName());
		}
		if (volumeCreateConf.getDescription() != null) {
			createVolumeOptions.description(volumeCreateConf.getDescription());
		}
		if (volumeCreateConf.getVolumeSnapshotId() != null) {
			Snapshot snapshot = cinderApi.getSnapshotApi(volumeCreateConf.getRegion()).get(volumeCreateConf.getVolumeSnapshotId());
			if (snapshot == null) {
				throw new ResourceNotFoundException("Volume Snapshot", "云硬盘快照", volumeCreateConf.getVolumeSnapshotId());
			} else {
				if (snapshot.getSize() > volumeCreateConf.getSize()) {
					throw new UserOperationException("Volume size can not be less than volume snapshot size.", "云硬盘的大小不能小于云硬盘快照的大小");
				}
				createVolumeOptions.snapshotId(volumeCreateConf.getVolumeSnapshotId());
			}
		}
		Integer count = volumeCreateConf.getCount();
		if (count == null) {
			count = 1;
		}
		if (count <= 0) {
			throw new UserOperationException(
					"The count of volume is less than or equal to zero.",
					"云硬盘的数量不能小于或等于0");
		}

		VolumeApi volumeApi = cinderApi.getVolumeApi(volumeCreateConf.getRegion());

		{
			List<? extends Volume> volumes = volumeApi.list().toList();
			List<? extends Snapshot> snapshots = cinderApi.getSnapshotApi(volumeCreateConf.getRegion()).list().toList();
			int pureVolumeSize = 0;
			for (Volume volume : volumes) {
				pureVolumeSize += volume.getSize();
			}
			long userVoUserId=openStackUser.getUserVoUserId();
			String region=volumeCreateConf.getRegion();

			LocalCommonQuotaSerivce localCommonQuotaSerivce = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce();
			localCommonQuotaSerivce.checkQuota(userVoUserId, region, CommonQuotaType.CLOUDVM_VOLUME, volumes.size() + count);
			localCommonQuotaSerivce.checkQuota(userVoUserId, region, CommonQuotaType.CLOUDVM_VOLUME_SIZE, pureVolumeSize + count * volumeCreateConf.getSize());

			VolumeQuota volumeQuota = cinderApi.getQuotaApi(volumeCreateConf.getRegion())
					.getByTenant(openStackUser.getTenantId());
			if (volumeQuota == null) {
				throw new OpenStackException(
						"Volume quota is not available.", "云硬盘配额不可用。");
			}
			if (sumGigabytes(volumes, snapshots) + count * volumeCreateConf.getSize() > volumeQuota.getGigabytes()) {
				throw new UserOperationException(
						"Volume size exceeding the quota.",
						"云硬盘大小超过配额。");
			}
			if (volumes.size() + count > volumeQuota.getVolumes()) {
				throw new UserOperationException(
						"Volume count exceeding the quota.",
						"云硬盘数量超过配额。");
			}
		}

		for (int i = 0; i < count; i++) {
			Volume volume = volumeApi.create(volumeCreateConf.getSize(), createVolumeOptions);
			CloudvmVolume cloudvmVolume = OpenStackServiceImpl.getOpenStackServiceGroup()
					.getLocalVolumeService()
					.create
							(openStackUser.getUserVoUserId(), openStackUser.getUserVoUserId(), volumeCreateConf.getRegion(), volume);
			OpenStackServiceImpl.getOpenStackServiceGroup().getVolumeSyncService().syncStatus
					(cloudvmVolume, new Checker<Volume>() {
						@Override
						public boolean check(Volume volume) throws Exception {
							return volume.getStatus() != Status.CREATING;
						}
					});
			if (successCreatedVolumes != null) {
				successCreatedVolumes.add(volume);
			}
		}
	}

	public void create(CinderApi cinderApi, VolumeCreateConf volumeCreateConf, VolumeCreateListener listener, Object listenerUserData)
			throws OpenStackException {
		List<Volume> successCreatedVolumes = null;
		if (listener != null) {
			successCreatedVolumes = new LinkedList<Volume>();
		}

		try {
			create(cinderApi, volumeCreateConf, listener, listenerUserData, successCreatedVolumes);
		} catch (Exception ex){
			notifyVolumeCreateListener(volumeCreateConf,successCreatedVolumes,ex,listener,listenerUserData);
			ExceptionUtil.throwException(ex);
		}
		notifyVolumeCreateListener(volumeCreateConf,successCreatedVolumes,null,listener,listenerUserData);
	}

	private void notifyVolumeCreateListener(VolumeCreateConf volumeCreateConf, List<Volume> successCreatedVolumes, Exception exception, VolumeCreateListener listener, Object listenerUserData) {
		if (listener != null) {
			int successCreatedVolumesCount = successCreatedVolumes.size();
			int volumesCount = volumeCreateConf.getCount();
			int volumeIndex = 0;

			for (; volumeIndex < successCreatedVolumesCount; volumeIndex++) {
				try {
					listener.volumeCreated(new VolumeCreateEvent(volumeCreateConf.getRegion(), successCreatedVolumes.get(volumeIndex).getId(), volumeIndex, listenerUserData));
				} catch (Exception e) {
					ExceptionUtil.processBillingException(e);
				}
			}

			String reason = exception != null ? ExceptionUtil.getUserMessage(exception) : "后台错误";
			for (; volumeIndex < volumesCount; volumeIndex++) {
				try {
					listener.volumeCreateFailed(new VolumeCreateFailEvent(volumeCreateConf.getRegion(), volumeIndex, reason, listenerUserData));
				} catch (Exception e) {
					ExceptionUtil.processBillingException(e);
				}
			}
		}
	}

	@Override
	public void delete(final String region, final VolumeResource volumeResource)
			throws OpenStackException {
		checkVolumeOperational(openStackUser.getUserVoUserId(),region,volumeResource.getId());
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				Status status = ((VolumeResourceImpl) volumeResource).volume
						.getStatus();
				if (status != Status.AVAILABLE && status != Status.ERROR) {
					throw new UserOperationException(
							"Volume is not removable.", "云硬盘不是可删除的状态。");
				}

				List<? extends Snapshot> snapshots = cinderApi.getSnapshotApi(region).list().toList();
				for (Snapshot snapshot : snapshots) {
					if (StringUtils.equals(snapshot.getVolumeId(), volumeResource.getId())) {
						throw new UserOperationException("There is a snapshot of the volume.", "云硬盘有快照，请先把云硬盘的快照删掉，再删除云硬盘。");
					}
				}

				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
				boolean isSuccess = volumeApi.delete(volumeResource.getId());
				if (!isSuccess) {
					throw new OpenStackException(MessageFormat.format(
							"Volume \"{0}\" delete failed.",
							volumeResource.getId()), MessageFormat.format(
							"云硬盘“{0}”删除失败。", volumeResource.getId()));
				}

				OpenStackServiceImpl.getOpenStackServiceGroup().getEventPublishService()
						.onDelete(new ResourceLocator().region(region).id(volumeResource.getId()).type(VolumeResource.class));

				return null;
			}

		});
	}

	@Override
	public void deleteSync(final String region, final VolumeResource volumeResource)
			throws OpenStackException {
		delete(region, volumeResource);

		waitingVolume(region, volumeResource.getId(), new VolumeChecker() {

			@Override
			public boolean check(Volume volume) {
				return volume == null;
			}
		});

		OpenStackServiceImpl.getOpenStackServiceGroup().getLocalVolumeService().delete(openStackUser.getUserVoUserId(),region,volumeResource.getId());
	}

	public void waitingVolume(final String region, final String volumeId,
			final VolumeChecker checker) throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				try {
					VolumeApi volumeApi = cinderApi.getVolumeApi(region);
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

	public void waitingVolume(VolumeApi volumeApi, String volumeId,
			long sleepTime, Checker<Volume> checker) throws OpenStackException {
		try {
			while (true) {
				Volume volume = volumeApi.get(volumeId);
				if (checker.check(volume)) {
					Thread.sleep(sleepTime);
				} else {
					break;
				}
			}
		} catch (OpenStackException e) {
			throw e;
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		} catch (Exception e) {
			throw new OpenStackException("后台错误", e);
		}
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

	public void delete(final String region, final List<Volume> volumes)
			throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {

			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
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
	public List<VolumeTypeResource> listVolumeType(final String region)
			throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, List<VolumeTypeResource>>() {

			@Override
			public List<VolumeTypeResource> run(CinderApi cinderApi)
					throws Exception {
				checkRegion(region);

				VolumeTypeApi volumeTypeApi = cinderApi
						.getVolumeTypeApi(region);

				List<VolumeTypeResource> volumeTypeResources = new LinkedList<VolumeTypeResource>();
				for (VolumeType volumeType : volumeTypeApi.list().toList()) {
					volumeTypeResources.add(new VolumeTypeResourceImpl(region,
							volumeType));
				}

				return volumeTypeResources;
			}
		});
	}

	@Override
	public Page listVolumeSnapshot(final String region, final String name, final Integer currentPage, final Integer recordsPerPage) throws OpenStackException {
		return runWithApi(new ApiRunnable<CinderApi, Page>() {
			@Override
			public Page run(final CinderApi cinderApi) throws Exception {
				checkRegion(region);

				final Map<String, Volume> idToVolume = new HashMap<String, Volume>();
				final Ref<List<? extends Snapshot>> volumeSnapshotsRef = new Ref<List<? extends Snapshot>>();
				final Page page;
				if (currentPage == null || recordsPerPage == null) {
					page = new Page();
				} else {
					page = new Page(currentPage, recordsPerPage);
				}

				ThreadUtil.concurrentRunAndWait(new Function<Void>() {
					@Override
					public Void apply() {
						List<? extends Snapshot> allVolumeSnapshots = cinderApi.getSnapshotApi(region).list().toList();
						page.setTotalRecords(allVolumeSnapshots.size());
						if (currentPage == null || recordsPerPage == null) {
							volumeSnapshotsRef.set(allVolumeSnapshots);
						} else {
							int fromIndex = (currentPage - 1) * recordsPerPage;
							int toIndex = fromIndex + recordsPerPage;
							toIndex = toIndex < allVolumeSnapshots.size() ? toIndex : allVolumeSnapshots.size();
							try {
								volumeSnapshotsRef.set(allVolumeSnapshots.subList(fromIndex, toIndex));
							} catch (IndexOutOfBoundsException e) {
								volumeSnapshotsRef.set(new ArrayList<Snapshot>());
							}
						}
						return null;
					}
				}, new Function<Void>() {
					@Override
					public Void apply() {
						List<? extends Volume> volumes = cinderApi.getVolumeApi(region).list().toList();
						for (Volume volume : volumes) {
							idToVolume.put(volume.getId(), volume);
						}
						return null;
					}
				});

				List<VolumeSnapshotResource> volumeSnapshotResources = new LinkedList<VolumeSnapshotResource>();
				for (Snapshot snapshot : volumeSnapshotsRef.get()) {
					volumeSnapshotResources.add(new VolumeSnapshotResourceImpl(region, snapshot, idToVolume.get(snapshot.getVolumeId())));
				}
				page.setData(volumeSnapshotResources);
				return page;
			}
		});
	}

	public static int sumGigabytes(List<? extends Volume> volumes, List<? extends Snapshot> snapshots) {
		int gibabytes = 0;
		for (Volume volume : volumes) {
			gibabytes += volume.getSize();
		}
		for (Snapshot snapshot : snapshots) {
			gibabytes += snapshot.getSize();
		}
		return gibabytes;
	}

	@Override
	public void createVolumeSnapshot(final String region, final String volumeId, final String name, final String description) throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {
			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				VolumeApi volumeApi = cinderApi.getVolumeApi(region);
				SnapshotApi snapshotApi = cinderApi.getSnapshotApi(region);

				Volume volume = volumeApi.get(volumeId);
				if (volume == null) {
					throw new ResourceNotFoundException("Volume", "云硬盘", volumeId);
				}

				VolumeQuota quota = cinderApi.getQuotaApi(region).getByTenant(openStackUser.getTenantId());
				if (quota == null) {
					throw new OpenStackException(
							"Volume snapshot quota is not available.", "云硬盘快照配额不可用。");
				}
				List<? extends Volume> volumes = volumeApi.list().toList();
				List<? extends Snapshot> snapshots = snapshotApi.list().toList();
				OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce()
						.checkQuota(openStackUser.getUserVoUserId(), region, CommonQuotaType.CLOUDVM_VOLUME_SNAPSHOT, snapshots.size() + 1);
				if (sumGigabytes(volumes, snapshots) + volume.getSize() > quota.getGigabytes()) {
					throw new UserOperationException(
							"Volume snapshot size exceeding the quota.",
							"云硬盘快照大小超过配额。");
				}
				if (snapshots.size() + 1 > quota.getSnapshots()) {
					throw new UserOperationException(
							"Volume snapshot count exceeding the quota.",
							"云硬盘快照数量超过配额。");
				}

				CreateSnapshotOptions createSnapshotOptions = CreateSnapshotOptions.Builder.name(name).description(description);
				if (volume.getAttachments() != null && !volume.getAttachments().isEmpty()) {
					createSnapshotOptions = createSnapshotOptions.force();
				}
				snapshotApi.create(volumeId, createSnapshotOptions);

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.incRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.VOLUME_SNAPSHOT);

				return null;
			}
		});
	}

	@Override
	public void deleteVolumeSnapshot(final String region, final String snapshotId) throws OpenStackException {
		runWithApi(new ApiRunnable<CinderApi, Void>() {
			@Override
			public Void run(CinderApi cinderApi) throws Exception {
				checkRegion(region);

				SnapshotApi snapshotApi = cinderApi.getSnapshotApi(region);

				Snapshot snapshot = snapshotApi.get(snapshotId);
				if (snapshot == null) {
					throw new ResourceNotFoundException("Volume Snapshot", "云硬盘快照", snapshotId);
				}

				Status status = snapshot.getStatus();
				if (status != Status.AVAILABLE && status != Status.ERROR) {
					throw new UserOperationException(
							"Volume snapshot is not removable.", "云硬盘快照不是可删除的状态。");
				}

				boolean isSuccess = snapshotApi.delete(snapshotId);
				if (!isSuccess) {
					throw new OpenStackException(MessageFormat.format(
							"Volume snapshot \"{0}\" delete failed.",
							snapshotId), MessageFormat.format(
							"云硬盘快照“{0}”删除失败。", snapshotId));
				}

				LocalRcCountService localRcCountService = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalRcCountService();
				localRcCountService.decRcCount(openStackUser.getUserVoUserId(), region, CloudvmRcCountType.VOLUME_SNAPSHOT);

				waitingVolumeSnapshot(snapshotApi, snapshotId, new Checker<Snapshot>() {
					@Override
					public boolean check(Snapshot snapshot) throws Exception {
						return snapshot == null;
					}
				});

				return null;
			}
		});
	}

	public void waitingVolumeSnapshot(final SnapshotApi snapshotApi, final String snapshotId,
									  final Checker<Snapshot> checker) throws OpenStackException {
		try {
			Snapshot snapshot = null;
			while (true) {
				snapshot = snapshotApi.get(snapshotId);
				if (checker.check(snapshot)) {
					break;
				}
				Thread.sleep(1000);
			}
		} catch (InterruptedException e) {
			throw new PollingInterruptedException(e);
		} catch (OpenStackException e) {
			throw e;
		} catch (Exception e) {
			throw new OpenStackException("后台错误", e);
		}
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

	@Override
	public <ReturnType> ReturnType runWithApi(ApiRunnable<CinderApi, ReturnType> task) throws OpenStackException {
		try {
			CinderApi api = OpenStackServiceImpl.getOpenStackServiceGroup().getApiService().getCinderApi();
			return task.run(api);
		} catch (OpenStackException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new OpenStackException("后台错误", ex);
		}
	}

	public void setVmManager(VMManagerImpl vmManager) {
		this.vmManager = vmManager;
	}
}
