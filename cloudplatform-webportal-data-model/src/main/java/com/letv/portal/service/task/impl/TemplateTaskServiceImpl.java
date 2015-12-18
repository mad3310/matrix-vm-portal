package com.letv.portal.service.task.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITemplateTaskDao;
import com.letv.portal.model.task.TemplateTask;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.task.ITemplateTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("templateTaskService")
public class TemplateTaskServiceImpl extends BaseServiceImpl<TemplateTask> implements ITemplateTaskService {
	
	private final static Logger logger = LoggerFactory.getLogger(TemplateTaskServiceImpl.class);

	@Resource
	private ITemplateTaskDao templateTaskDao;
	
	public TemplateTaskServiceImpl() {
		super(TemplateTask.class);
	}
	
	@Override
	public IBaseDao<TemplateTask> getDao() {
		return templateTaskDao;
	}

	@Override
	public TemplateTask selectByName(String name) {
		return this.templateTaskDao.selectByName(name);
	}
}
