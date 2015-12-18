package com.letv.portal.service.task;

import com.letv.portal.model.task.TemplateTaskChain;
import com.letv.portal.service.common.IBaseService;

import java.util.List;

public interface ITemplateTaskChainService extends IBaseService<TemplateTaskChain>{

	List<TemplateTaskChain> selectByTemplateTaskId(Long templateId);
	
}
