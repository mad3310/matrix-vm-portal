package com.letv.portal.service.openstack.resource.manager;

import java.util.List;

import com.letv.portal.service.openstack.resource.VMResource;

public interface VMManager extends ResourceManager{
	List<VMResource> list();
	
	VMResource create(VMCreateConf conf) ;
	void delete(VMResource vm);
	void start(VMResource vm);
	void stop(VMResource vm);
}
