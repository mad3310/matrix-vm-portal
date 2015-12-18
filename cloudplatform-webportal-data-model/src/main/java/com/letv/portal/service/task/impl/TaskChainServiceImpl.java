package com.letv.portal.service.task.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITaskChainDao;
import com.letv.portal.model.task.TaskChain;
import com.letv.portal.model.task.TaskExecuteStatus;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.task.ITaskChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("taskChainService")
public class TaskChainServiceImpl extends BaseServiceImpl<TaskChain> implements
		ITaskChainService {

	private final static Logger logger = LoggerFactory
			.getLogger(TaskChainServiceImpl.class);

	@Resource
	private ITaskChainDao taskChainDao;

	public TaskChainServiceImpl() {
		super(TaskChain.class);
	}

	@Override
	public IBaseDao<TaskChain> getDao() {
		return taskChainDao;
	}

	@Override
	public TaskChain selectNextChainByIndexAndOrder(Long chainIndexId,
			int executeOrder) {
		TaskChain tc = new TaskChain();
		tc.setChainIndexId(chainIndexId);
		tc.setExecuteOrder(executeOrder);
		return this.taskChainDao.selectNextChainByIndexAndOrder(tc);
	}

	@Override
	public TaskChain selectFailedChainByIndex(long chainIndexId) {
		TaskChain tc = new TaskChain();
		tc.setChainIndexId(chainIndexId);
		tc.setStatus(TaskExecuteStatus.FAILED);
		return this.taskChainDao.selectFailedChainByIndex(tc);
	}

	@Override
	public List<TaskChain> selectAllChainByIndexId(Long chainIndexId) {
		return this.taskChainDao.selectAllChainByIndexId(chainIndexId);
	}

	@Override
	public void updateAfterDoingChainStatus(Map<String, Object> params) {
		this.taskChainDao.updateAfterDoingChainStatus(params);

	}

	private int getStepByTaskChainIndexId(Long taskChainIndexId) {
		List<TaskChain> taskChains = this
				.selectAllChainByIndexId(taskChainIndexId);

		if (taskChains.get(taskChains.size() - 1).getStatus() == TaskExecuteStatus.SUCCESS) {
			return 0;// 返回创建成功
		}
		for (TaskChain taskChain : taskChains) {
			if (taskChain.getStatus() == TaskExecuteStatus.FAILED) {
				return -1;// 返回创建失败
			} else if (taskChain.getStatus() == TaskExecuteStatus.DOING) {
				return taskChain.getExecuteOrder();// 返回此步所在任务中的顺序
			}
		}
		return 1;// 都没有，则认为正在执行第一步
	}
	
}
