package com.letv.lcp.openstack.service.manage;

import java.util.List;

import com.letv.common.paging.impl.Page;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.openstack.exception.APINotAvailableException;
import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.exception.RegionNotFoundException;
import com.letv.lcp.openstack.exception.ResourceNotFoundException;
import com.letv.lcp.openstack.model.storage.VolumeResource;
import com.letv.lcp.openstack.model.storage.VolumeTypeResource;
import com.letv.lcp.openstack.util.Ref;

public interface VolumeManager extends ResourceManager {

	VolumeResource get(String region, String id)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	Page listAll(String name, Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	Page listByRegionGroup(String regionGroup, String name,
			Integer currentPage, Integer recordsPerPage)
			throws RegionNotFoundException, ResourceNotFoundException,
			APINotAvailableException, OpenStackException;

	Page list(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

	void create(VolumeCreateConf volumeCreateConf) throws RegionNotFoundException, OpenStackException;

//	void delete(String region, VolumeResource volumeResource)
//			throws OpenStackException;

//	void deleteSync(String region, VolumeResource volumeResource)
//			throws OpenStackException;

	List<VolumeTypeResource> listVolumeType(String region) throws OpenStackException;

	Page listVolumeSnapshot(String region, String name, Integer currentPage, Integer recordsPerPage) throws OpenStackException;

	void createVolumeSnapshot(String region, String volumeId, String name, String description) throws OpenStackException;

	void deleteVolumeSnapshot(String region, String snapshotId, Ref<String> nameRef) throws OpenStackException;

	void checkCreate(VolumeCreateConf volumeCreateConf) throws OpenStackException;

    VolumeTypeResource getVolumeTypeResource(String region, String volumeTypeId) throws OpenStackException;
}
