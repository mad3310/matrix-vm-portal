package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

public interface VmCreateSubTask {
	void run(VmCreateContext context);
	void rollback(VmCreateContext context);
}
