package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.model.cloudvm.CloudvmVolumeStatus;
import com.letv.portal.model.common.CommonQuotaType;
import com.letv.portal.service.openstack.erroremail.impl.ErrorMailMessageModel;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.impl.OpenStackServiceGroup;
import com.letv.portal.service.openstack.impl.OpenStackServiceImpl;
import com.letv.portal.service.openstack.local.service.LocalCommonQuotaSerivce;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;
import com.letv.portal.service.openstack.resource.manager.impl.VolumeManagerImpl;
import org.jclouds.openstack.cinder.v1.domain.Snapshot;
import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.VolumeQuota;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;

import java.text.MessageFormat;
import java.util.List;

public class CreateVolumeTask extends VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		if (context.getVmCreateConf().getVolumeSize() == 0) {
			return;
		}

		List<? extends Volume> volumes = context.getApiCache().getVolumeApi().list().toList();
		List<? extends Snapshot> snapshots = context.getApiCache().getVolumeSnapshotApi().list().toList();
		int pureVolumeSize = 0;
		for (Volume volume : volumes) {
			pureVolumeSize += volume.getSize();
		}

		String region = context.getVmCreateConf().getRegion();
		long tenantId = context.getUserId();
		int count = context.getVmCreateConf().getCount();
		LocalCommonQuotaSerivce localCommonQuotaSerivce = OpenStackServiceImpl.getOpenStackServiceGroup().getLocalCommonQuotaSerivce();
		localCommonQuotaSerivce.checkQuota(tenantId, region, CommonQuotaType.CLOUDVM_VOLUME, volumes.size() + count);
		localCommonQuotaSerivce.checkQuota(tenantId, region, CommonQuotaType.CLOUDVM_VOLUME_SIZE, pureVolumeSize + count * context.getVmCreateConf().getVolumeSize());

		VolumeQuota quota = context
				.getApiCache()
				.getCinderQuotaApi()
				.getByTenant(
						context.getVmManager().getOpenStackUser().getTenantId());
		if (quota == null) {
			throw new OpenStackException("Cinder quota is not available.",
					"云硬盘的配额不可用。");
		}
		if (volumes.size() + context.getVmCreateConf().getCount() > quota
				.getVolumes()) {
			throw new UserOperationException(
					"Volume count exceeding the quota.", "云硬盘数量超过配额。");
		}
		if (VolumeManagerImpl.sumGigabytes(volumes,snapshots) + context.getVmCreateConf().getCount()
				* context.getVmCreateConf().getVolumeSize() > quota
					.getGigabytes()) {
			throw new UserOperationException(
					"Volumes size exceeding the quota.", "云硬盘总大小超过配额。");
		}

		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			Volume volume=context
					.getApiCache()
					.getVolumeApi()
					.create(context.getVmCreateConf().getVolumeSize(),
							new CreateVolumeOptions().volumeType(context
									.getVolumeType().getId()).name(vmCreateContext.getResourceName()));
			vmCreateContext.setVolume(volume);
			OpenStackServiceImpl.getOpenStackServiceGroup()
					.getLocalVolumeService()
					.create(context.getUserId(), context.getUserId(), context.getVmCreateConf().getRegion(), volume, CloudvmVolumeStatus.WAITING_ATTACHING);
		}
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
		if (context.getVmCreateConf().getVolumeSize() == 0) {
			return;
		}

		Checker<Volume> volumeChecker = new Checker<Volume>() {

			@Override
			public boolean check(Volume volume) throws Exception {
				return volume.getStatus() == Volume.Status.CREATING;
			}
		};
		OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			if (vmCreateContext.getServerCreated() == null
					&& vmCreateContext.getVolume() != null) {
				final String volumeId = vmCreateContext.getVolume().getId();
				context.getVolumeManager().waitingVolume(
						context.getApiCache().getVolumeApi(), volumeId, 100,
						volumeChecker);
				boolean isSuccess = context.getApiCache().getVolumeApi().delete(volumeId);
				if (isSuccess) {
					openStackServiceGroup
							.getLocalVolumeService()
							.delete(context.getUserId(), context.getVmCreateConf().getRegion(), volumeId);
				} else {
					openStackServiceGroup.getErrorEmailService()
							.sendErrorEmail(
									new ErrorMailMessageModel()
											.exceptionMessage("创建云主机的云硬盘回滚时删除失败")
											.exceptionParams(MessageFormat.format("userId={0},region={1},volumeId={2}", context.getUserId(),context.getVmCreateConf().getRegion(), volumeId))
											.toMap());
				}
			}
//            if (vmCreateContext.getServerCreated() != null && vmCreateContext.getVolume() != null) {
//                OpenStackServiceGroup openStackServiceGroup = OpenStackServiceImpl.getOpenStackServiceGroup();
//                CloudvmVolume cloudvmVolume = openStackServiceGroup.getCloudvmVolumeService()
//                        .selectByVolumeId(context.getUserId(), context.getVmCreateConf().getRegion(), vmCreateContext.getVolume().getId());
//                openStackServiceGroup.getVolumeSyncService().syncStatus(cloudvmVolume, new Checker<Volume>() {
//                    @Override
//                    public boolean check(Volume volume) throws Exception {
//                        return volume.getStatus() != Volume.Status.CREATING;
//                    }
//                });
//            }
		}
	}

}
