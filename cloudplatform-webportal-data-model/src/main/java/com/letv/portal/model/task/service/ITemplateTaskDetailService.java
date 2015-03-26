package com.letv.portal.model.task.service;

import java.util.List;

import com.letv.portal.model.task.TemplateTaskDetail;
import com.letv.portal.service.IBaseService;

public interface ITemplateTaskDetailService extends IBaseService<TemplateTaskDetail> {

	List <TemplateTaskDetail> selectByTemplateTaskType(String type);
	
}
