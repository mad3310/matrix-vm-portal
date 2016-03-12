package com.letv.portal.service.cmdb;

import com.letv.common.result.ResultObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateForm;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.model.order.Order;


/**
 * cmdb相关
 * @author lisuxiao
 *
 */
public interface ICmdbService {
	
	VMCreateConf2 getCreateVmConf(VmCreateForm vmCreateForm, CloudvmRegion vmRegion);
	
	void createSession(VMCreateConf2 conf, CloudvmRegion vmRegion) throws OpenStackException;
	
	void createVmInfo(Long id, String paramsData, String groupId, ResultObject obj);
	
}
