package com.letv.portal.service.adminoplog;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.adminoplog.AdminOpLog;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.service.IBaseService;

public interface IAdminOpLogService extends IBaseService<AdminOpLog> {

	void add(String event, AoLogType logType, String module,
			String description);

	void delete(Long id);

	Page select(AdminOpLogQueryCondition condition, Integer currentPage,
			Integer recordsPerPage);

	void deleteRecent(Integer days);
}
