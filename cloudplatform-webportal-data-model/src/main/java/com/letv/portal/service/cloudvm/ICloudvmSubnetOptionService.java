package com.letv.portal.service.cloudvm;

import com.letv.portal.model.cloudvm.CloudvmSubnetOption;
import com.letv.portal.service.common.IBaseService;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/11/25.
 */
public interface ICloudvmSubnetOptionService extends IBaseService<CloudvmSubnetOption> {
    List<CloudvmSubnetOption> selectAll();
}
