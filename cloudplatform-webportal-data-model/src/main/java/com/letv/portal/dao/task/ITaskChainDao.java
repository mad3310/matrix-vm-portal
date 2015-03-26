package com.letv.portal.dao.task;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.task.TaskChain;

public interface ITaskChainDao extends IBaseDao<TaskChain> {

	TaskChain selectNextChainByIndexAndOrder(TaskChain tc);

	TaskChain selectFailedChainByIndex(TaskChain tc);

	List<TaskChain> selectAllChainByIndexId(Long chainIndexId);

}
