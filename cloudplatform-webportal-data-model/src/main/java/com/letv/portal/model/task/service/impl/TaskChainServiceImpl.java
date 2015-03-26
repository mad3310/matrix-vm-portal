package com.letv.portal.model.task.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITaskChainDao;
import com.letv.portal.model.task.TaskChain;
import com.letv.portal.model.task.TaskExecuteStatus;
import com.letv.portal.model.task.service.ITaskChainService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("taskChainService")
public class TaskChainServiceImpl extends BaseServiceImpl<TaskChain> implements ITaskChainService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskChainServiceImpl.class);

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
	public TaskChain selectNextChainByIndexAndOrder(Long chainIndexId, int executeOrder) {
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
	
}
