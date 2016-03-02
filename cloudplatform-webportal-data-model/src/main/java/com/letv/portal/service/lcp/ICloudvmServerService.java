package com.letv.portal.service.lcp;

import com.letv.portal.model.cloudvm.lcp.CloudvmServerModel;
import com.letv.portal.service.common.IBaseService;

public interface ICloudvmServerService extends IBaseService<CloudvmServerModel> {
	CloudvmServerModel selectByServerInstanceId(String serverInstanceId);
}
