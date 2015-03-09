package com.letv.portal.dao.task;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.task.TemplateTaskChain;

public interface ITemplateTaskChainDao extends IBaseDao<TemplateTaskChain> {

	public List<TemplateTaskChain> selectByTemplateTaskId(Long taskId);
}
