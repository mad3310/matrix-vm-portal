package com.letv.portal.service.cloudvm;

import com.letv.portal.model.cloudvm.CloudvmServer;
import com.letv.portal.service.IBaseService;

/**
 * Created by zhouxianguang on 2015/9/18.
 */
public interface ICloudvmServerService extends IBaseService<CloudvmServer> {
    CloudvmServer selectByServerId(String region, String serverId);
    int selectByUserIdCount(long userId);
}
