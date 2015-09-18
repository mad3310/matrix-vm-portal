package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import org.jclouds.openstack.cinder.v1.domain.Volume;
import org.jclouds.openstack.cinder.v1.domain.VolumeQuota;
import org.jclouds.openstack.cinder.v1.options.CreateVolumeOptions;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.UserOperationException;
import com.letv.portal.service.openstack.resource.manager.impl.Checker;

public class CreateVolumeTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		if (context.getVmCreateConf().getVolumeSize() == 0) {
			return;
		}

		int totalVolumeCount = 0, totalVolumeSize = 0;
		for (Volume volume : context.getApiCache().getVolumeApi().list()
				.toList()) {
			totalVolumeCount++;
			totalVolumeSize += volume.getSize();
		}

		VolumeQuota quota = context
				.getApiCache()
				.getCinderQuotaApi()
				.getByTenant(
						context.getVmManager().getOpenStackUser().getTenantId());
		if (quota == null) {
			throw new OpenStackException("Cinder quota is not available.",
					"云硬盘的配额不可用。");
		}
		if (totalVolumeCount + context.getVmCreateConf().getCount() > quota
				.getVolumes()) {
			throw new UserOperationException(
					"Volume count exceeding the quota.", "云硬盘数量超过配额。");
		}
		if (totalVolumeSize + context.getVmCreateConf().getCount()
				* context.getVmCreateConf().getVolumeSize() > quota
					.getGigabytes()) {
			throw new UserOperationException(
					"Volumes size exceeding the quota.", "云硬盘总大小超过配额。");
		}

		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			vmCreateContext.setVolume(context
					.getApiCache()
					.getVolumeApi()
					.create(context.getVmCreateConf().getVolumeSize(),
							new CreateVolumeOptions().volumeType(context
									.getVolumeType().getId())));
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
		for (VmCreateContext vmCreateContext : context.getVmCreateContexts()) {
			if (vmCreateContext.getServerCreated() == null
					&& vmCreateContext.getVolume() != null) {
				final String volumeId = vmCreateContext.getVolume().getId();
				context.getVolumeManager().waitingVolume(
						context.getApiCache().getVolumeApi(), volumeId, 100,
						volumeChecker);
				context.getApiCache().getVolumeApi().delete(volumeId);
			}
		}
	}

}
