package com.letv.portal.service.cloudvm;

import com.letv.portal.model.cloudvm.CloudvmFlavor;
import com.letv.portal.service.IBaseService;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmFlavorService extends IBaseService<CloudvmFlavor> {
    CloudvmFlavor selectByFlavorId(String region, String flavorId);
}
