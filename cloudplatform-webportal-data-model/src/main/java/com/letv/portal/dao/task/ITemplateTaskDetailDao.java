package com.letv.portal.dao.task;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.task.TemplateTaskDetail;
import org.springframework.stereotype.Repository;

@Repository("templateTaskDetailDao")
public interface ITemplateTaskDetailDao extends IBaseDao<TemplateTaskDetail> {

	List<TemplateTaskDetail> selectByTemplateTaskType(String type);

}
