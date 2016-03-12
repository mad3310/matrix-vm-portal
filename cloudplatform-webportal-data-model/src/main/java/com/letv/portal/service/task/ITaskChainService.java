package com.letv.portal.service.task;

import com.letv.portal.model.task.TaskChain;
import com.letv.portal.service.common.IBaseService;

import java.util.List;
import java.util.Map;

public interface ITaskChainService extends IBaseService<TaskChain> {

	TaskChain selectNextChainByIndexAndOrder(Long chainIndexId, int executeOrder);

	TaskChain selectFailedChainByIndex(long chainIndexId);

	List<TaskChain> selectAllChainByIndexId(Long chainIndexId);
	
	/**
	  * @Title: selectNotUndoChainByIndexIdAndOrder
	  * @Description: 根据联调序号和顺序查询不是undo状态的TaskChain
	  * @param params
	  * @return List<TaskChain>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年3月11日 上午11:03:37
	  */
	List<TaskChain> selectNotUndoChainByIndexIdAndOrder(Map<String, Object> params);

	void updateAfterDoingChainStatus(Map<String, Object> params);
	
	void updateStatusById(TaskChain taskChain);

}
