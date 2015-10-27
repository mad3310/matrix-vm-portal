package com.letv.portal.service.openstack.resource.manager.impl.create.vm;

import com.letv.portal.service.openstack.exception.OpenStackException;

public abstract class VmsCreateSubTask {
	void run(MultiVmCreateContext context) throws OpenStackException{}

	void rollback(MultiVmCreateContext context) throws OpenStackException{}

	boolean needContinueAfterException(){
		return false;
	}

	boolean isEnable(MultiVmCreateContext context){
		return true;
	}
}
