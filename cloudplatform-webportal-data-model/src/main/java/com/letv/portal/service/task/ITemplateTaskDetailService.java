package com.letv.portal.service.task;

import com.letv.portal.model.task.TemplateTaskDetail;
import com.letv.portal.service.common.IBaseService;

import java.util.List;

public interface ITemplateTaskDetailService extends IBaseService<TemplateTaskDetail> {

	List <TemplateTaskDetail> selectByTemplateTaskType(String type);
	
}
