package com.letv.portal.model.task.service;

import java.util.List;

import com.letv.portal.model.task.TemplateTaskChain;
import com.letv.portal.service.IBaseService;

public interface ITemplateTaskChainService extends IBaseService<TemplateTaskChain>{

	List<TemplateTaskChain> selectByTemplateTaskId(Long templateId);
	
}
