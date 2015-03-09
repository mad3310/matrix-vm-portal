package com.letv.portal.model.task.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITemplateTaskDao;
import com.letv.portal.model.task.TemplateTask;
import com.letv.portal.model.task.service.ITemplateTaskService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("templateTaskService")
public class TemplateTaskServiceImpl extends BaseServiceImpl<TemplateTask> implements ITemplateTaskService{
	
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
	
}
