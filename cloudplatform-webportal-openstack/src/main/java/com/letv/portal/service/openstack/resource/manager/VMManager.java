package com.letv.portal.service.openstack.resource.manager;

import java.util.List;
import java.util.Map;

import com.letv.common.paging.impl.Page;
import com.letv.portal.service.openstack.billing.listeners.VmCreateListener;
import com.letv.portal.service.openstack.exception.APINotAvailableException;
import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.exception.PollingInterruptedException;
import com.letv.portal.service.openstack.exception.RegionNotFoundException;
import com.letv.portal.service.openstack.exception.ResourceNotFoundException;
import com.letv.portal.service.openstack.exception.TaskNotFinishedException;
import com.letv.portal.service.openstack.exception.VMDeleteException;
import com.letv.portal.service.openstack.exception.VMStatusException;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.manager.impl.create.vm.VMCreateConf2;

public interface VMManager extends ResourceManager {
	List<VMResource> list(String region) throws RegionNotFoundException,
			ResourceNotFoundException, APINotAvailableException,
			OpenStackException;

	Page listAll(String name, Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	Page listByRegionGroup(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	List<VMResource> listVmUnbindedFloatingIp(String region) throws OpenStackException;

	VMResource get(String region, String id) throws RegionNotFoundException,
			ResourceNotFoundException, APINotAvailableException,
			OpenStackException;

	VMResource create(String region, VMCreateConf conf)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, PollingInterruptedException,
			OpenStackException;

	void create2(VMCreateConf2 conf) throws OpenStackException;

	void createForBilling(long userId, VMCreateConf2 conf, VmCreateListener listener, Object listenerUserData) throws OpenStackException;

	void publish(String region, VMResource vm) throws RegionNotFoundException,
			APINotAvailableException, TaskNotFinishedException,
			VMStatusException, OpenStackException;

	void unpublish(String region, VMResource vm)
			throws RegionNotFoundException, APINotAvailableException,
			TaskNotFinishedException, VMStatusException, OpenStackException;

	void delete(String region, VMResource vm) throws RegionNotFoundException,
			VMDeleteException, APINotAvailableException,
			TaskNotFinishedException, OpenStackException;

	void deleteSync(String region, VMResource vm) throws VMDeleteException,
			RegionNotFoundException, APINotAvailableException,
			TaskNotFinishedException, PollingInterruptedException,
			OpenStackException;

	void batchDeleteSync(String vmIdListJson) throws OpenStackException;

	void start(String region, VMResource vm) throws RegionNotFoundException,
			VMStatusException, TaskNotFinishedException, OpenStackException;

	void startSync(String region, VMResource vm)
			throws RegionNotFoundException, TaskNotFinishedException,
			VMStatusException, PollingInterruptedException, OpenStackException;

	void batchStartSync(String vmIdListJson) throws OpenStackException;

	void stop(String region, VMResource vm) throws RegionNotFoundException,
			TaskNotFinishedException, VMStatusException, OpenStackException;

	void stopSync(String region, VMResource vm)
			throws PollingInterruptedException, RegionNotFoundException,
			TaskNotFinishedException, VMStatusException, OpenStackException;

	void batchStopSync(String regionAndVmIds) throws OpenStackException;

	int totalNumber() throws OpenStackException;

	List<FlavorResource> listFlavorResources(String region)
			throws RegionNotFoundException, OpenStackException;

	FlavorResource getFlavorResource(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			OpenStackException;

	Map<Integer, Map<Integer, Map<Integer, FlavorResource>>> groupFlavorResources(
			String region) throws OpenStackException;

	void attachVolume(VMResource vmResource, VolumeResource volumeResource)
			throws OpenStackException;

	void detachVolume(VMResource vmResource, VolumeResource volumeResource)
			throws OpenStackException;

	String openConsole(VMResource vmResource) throws APINotAvailableException,
			OpenStackException;

	void bindFloatingIp(String region, String vmId, String floatingIpId)
			throws OpenStackException;

	void unbindFloatingIp(String region, String vmId, String floatingIpId)
			throws OpenStackException;

	void createImageFromVm(VmSnapshotCreateConf createConf) throws OpenStackException;
}
