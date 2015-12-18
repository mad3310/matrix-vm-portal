package com.letv.portal.service.task.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.task.ITemplateTaskChainDao;
import com.letv.portal.model.task.TemplateTaskChain;
import com.letv.portal.service.common.impl.BaseServiceImpl;
import com.letv.portal.service.task.ITemplateTaskChainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("templateTaskChainService")
public class TemplateTaskChainServiceImpl extends BaseServiceImpl<TemplateTaskChain> implements ITemplateTaskChainService {
	
	private final static Logger logger = LoggerFactory.getLogger(TemplateTaskChainServiceImpl.class);

	@Resource
	private ITemplateTaskChainDao templateTaskChainDao;
	
	public TemplateTaskChainServiceImpl() {
		super(TemplateTaskChain.class);
	}
	
	@Override
	public IBaseDao<TemplateTaskChain> getDao() {
		return templateTaskChainDao;
	}
	
	@Override
	public List<TemplateTaskChain> selectByTemplateTaskId(Long templateId) {
		return this.templateTaskChainDao.selectByTemplateTaskId(templateId);
	}

	
}
