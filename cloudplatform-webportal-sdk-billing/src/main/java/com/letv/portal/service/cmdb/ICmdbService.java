package com.letv.portal.service.cmdb;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.letv.common.result.ResultObject;
import com.letv.lcp.cloudvm.model.task.VMCreateConf2;
import com.letv.lcp.cloudvm.model.task.VmCreateForm;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.portal.model.cloudvm.CloudvmCluster;
import com.letv.portal.model.cloudvm.CloudvmRegion;


/**
 * cmdb相关
 * @author lisuxiao
 *
 */
public interface ICmdbService {
	
	VMCreateConf2 collectCreateVmConf(VmCreateForm vmCreateForm, CloudvmCluster vmCluster);
	
	void createSession(VMCreateConf2 conf, CloudvmCluster vmCluster) throws OpenStackException;
	
	void createVmInfo(Long id, String paramsData, String groupId, ResultObject obj);
	
	List<Map<String, Object>> getFlavorsByClusterId(Long clusterId);
	List<Map<String, Object>> getFlavors();
	List<Map<String, Object>> getImagesByClusterId(Long clusterId);
	List<Map<String, Object>> getImages();
	List<Map<String, Object>> getClusters(Map<String,Object> params);
	
}
