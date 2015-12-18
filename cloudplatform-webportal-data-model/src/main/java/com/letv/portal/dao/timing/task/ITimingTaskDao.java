package com.letv.portal.dao.timing.task;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.timing.task.BaseTimingTask;
import org.springframework.stereotype.Repository;

@Repository("timingTaskDao")
public interface ITimingTaskDao extends IBaseDao<BaseTimingTask> {

}
