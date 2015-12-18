package com.letv.portal.dao.task;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.task.TemplateTask;
import org.springframework.stereotype.Repository;

@Repository("templateTaskDao")
public interface ITemplateTaskDao extends IBaseDao<TemplateTask> {

	TemplateTask selectByName(String name);

}
