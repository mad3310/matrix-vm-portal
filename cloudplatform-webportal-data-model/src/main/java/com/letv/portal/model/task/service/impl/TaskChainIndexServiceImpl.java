package com.letv.portal.model.task.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITaskChainIndexDao;
import com.letv.portal.model.task.TaskChainIndex;
import com.letv.portal.model.task.service.ITaskChainIndexService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("taskChainIndexService")
public class TaskChainIndexServiceImpl extends BaseServiceImpl<TaskChainIndex> implements ITaskChainIndexService{
	
	private final static Logger logger = LoggerFactory.getLogger(TaskChainIndexServiceImpl.class);

	@Resource
	private ITaskChainIndexDao taskChainIndexDao;
	
	public TaskChainIndexServiceImpl() {
		super(TaskChainIndex.class);
	}
	
	@Override
	public IBaseDao<TaskChainIndex> getDao() {
		return taskChainIndexDao;
	}
	
}
