package com.letv.portal.service.task;

import com.letv.portal.model.task.TaskChainIndex;
import com.letv.portal.service.common.IBaseService;

public interface ITaskChainIndexService extends IBaseService<TaskChainIndex>{

	TaskChainIndex  selectByServiceAndClusterName(String serviceName, String clusterName);
}
