package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;

public class CheckVolumeQuotaTask implements VmsCreateSubTask {

	@Override
	public void run(MultiVmCreateContext context) throws OpenStackException {
		if (context.getVmCreateConf().getVolumeSize() == 0) {
			return;
		}
		
		
	}

	@Override
	public void rollback(MultiVmCreateContext context)
			throws OpenStackException {
		if (context.getVmCreateConf().getVolumeSize() == 0) {
			return;
		}
		
		
	}

}
