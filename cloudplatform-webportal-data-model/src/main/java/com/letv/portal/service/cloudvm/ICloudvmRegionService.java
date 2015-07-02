package com.letv.portal.service.cloudvm;

import com.letv.portal.model.cloudvm.CloudvmRegion;
import com.letv.portal.service.IBaseService;

public interface ICloudvmRegionService extends IBaseService<CloudvmRegion> {
	void add(String code, String displayName);

	void edit(Long id, String code, String displayName);

	void remove(Long id);

	CloudvmRegion selectByCode(String code);
}
