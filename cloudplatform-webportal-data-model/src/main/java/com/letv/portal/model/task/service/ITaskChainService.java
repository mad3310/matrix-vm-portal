package com.letv.portal.model.task.service;

import com.letv.portal.model.task.TaskChain;
import com.letv.portal.service.IBaseService;

public interface ITaskChainService extends IBaseService<TaskChain>{

	TaskChain selectNextChainByIndexAndOrder(Long chainIndexId, int executeOrder);

	TaskChain selectFailedChainByIndex(long chainIndexId);
	
}
