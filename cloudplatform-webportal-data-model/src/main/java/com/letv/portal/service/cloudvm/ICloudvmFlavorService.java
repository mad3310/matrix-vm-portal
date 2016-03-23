package com.letv.portal.service.cloudvm;

import java.util.List;

import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.service.common.IBaseService;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmFlavorService extends IBaseService<CloudvmFlavor> {
	CloudvmFlavor selectByFlavorId(String region, String flavorId);

	List<CloudvmFlavor> selectByClusterId(Long clusterId);
}
