package com.letv.portal.model.task.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITemplateTaskDetailDao;
import com.letv.portal.model.task.TemplateTaskDetail;
import com.letv.portal.model.task.service.ITemplateTaskDetailService;
import com.letv.portal.service.impl.BaseServiceImpl;

@Service("templateTaskDetailService")
public class TemplateTaskDetailServiceImpl extends BaseServiceImpl<TemplateTaskDetail> implements ITemplateTaskDetailService{
	
	private final static Logger logger = LoggerFactory.getLogger(TemplateTaskDetailServiceImpl.class);

	@Resource
	private ITemplateTaskDetailDao templateTaskDetailDao;
	
	public TemplateTaskDetailServiceImpl() {
		super(TemplateTaskDetail.class);
	}
	
	@Override
	public IBaseDao<TemplateTaskDetail> getDao() {
		return templateTaskDetailDao;
	}

	@Override
	public List<TemplateTaskDetail> selectByTemplateTaskType(String type) {
		return this.templateTaskDetailDao.selectByTemplateTaskType(type);
	}
	
}
