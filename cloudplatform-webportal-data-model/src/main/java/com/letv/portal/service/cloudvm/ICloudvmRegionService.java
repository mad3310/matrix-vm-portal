package com.letv.portal.service.cloudvm;

import java.util.List;

import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.common.IBaseService;

public interface ICloudvmRegionService extends IBaseService<CloudvmRegion> {
	void add(String code, String displayName);

	void edit(Long id, String code, String displayName);

	void remove(Long id);

	CloudvmRegion selectByCode(String code);

	List<CloudvmRegion> selectAll();
}
