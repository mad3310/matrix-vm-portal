package com.letv.portal.timing.task.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.timing.task.ITimingTaskDao;
import com.letv.portal.model.timing.task.BaseTimingTask;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.timing.task.IBaseTimingTaskService;

@Service("baseTimingTaskService")
public class BaseTimingTaskServiceImpl  extends BaseServiceImpl<BaseTimingTask> implements IBaseTimingTaskService{

	private final static Logger logger = LoggerFactory.getLogger(BaseTimingTaskServiceImpl.class);
	
	@Resource
	private ITimingTaskDao timingTaskDao;

	public BaseTimingTaskServiceImpl() {
		super(BaseTimingTask.class);
	}

	@Override
	public IBaseDao<BaseTimingTask> getDao() {
		return this.timingTaskDao;
	}
	
}
