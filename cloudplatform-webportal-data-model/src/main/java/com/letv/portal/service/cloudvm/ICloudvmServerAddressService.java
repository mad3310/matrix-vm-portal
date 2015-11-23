package com.letv.portal.service.cloudvm;

import com.letv.portal.model.cloudvm.CloudvmServerAddress;
import com.letv.portal.service.IBaseService;

import java.util.List;

/**
 * Created by zhouxianguang on 2015/9/28.
 */
public interface ICloudvmServerAddressService extends IBaseService<CloudvmServerAddress> {
    List<CloudvmServerAddress> selectByRegionAndServerId(String region, String serverId);

    void deleteByRegionAndServerId(String region, String serverId);
}
