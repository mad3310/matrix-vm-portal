package com.letv.lcp.openstack.service.task.createvm;

import com.letv.lcp.openstack.exception.OpenStackException;

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
