package com.letv.portal.service.task;

import com.letv.portal.model.task.TaskChain;
import com.letv.portal.service.common.IBaseService;

import java.util.List;
import java.util.Map;

public interface ITaskChainService extends IBaseService<TaskChain> {

	TaskChain selectNextChainByIndexAndOrder(Long chainIndexId, int executeOrder);

	TaskChain selectFailedChainByIndex(long chainIndexId);

	List<TaskChain> selectAllChainByIndexId(Long chainIndexId);

	void updateAfterDoingChainStatus(Map<String, Object> params);

}
