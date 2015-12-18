package com.letv.portal.service.task;

import com.letv.portal.model.task.TemplateTask;
import com.letv.portal.service.common.IBaseService;

public interface ITemplateTaskService extends IBaseService<TemplateTask>{
	
	public TemplateTask selectByName(String name);
}
