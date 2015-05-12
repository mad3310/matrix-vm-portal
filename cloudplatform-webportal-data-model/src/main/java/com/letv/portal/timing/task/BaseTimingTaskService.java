package com.letv.portal.timing.task;

import com.letv.portal.model.timing.task.BaseTimingTask;

public interface BaseTimingTaskService {
	public String insert(BaseTimingTask task);
	public String update(BaseTimingTask task);
	public String delete(BaseTimingTask task);
	public String getById(Long id);
	public String list();
}
