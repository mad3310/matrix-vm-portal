package com.letv.portal.dao.adminoplog;

import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.adminoplog.AdminOpLog;

public interface IAdminOpLogDao extends IBaseDao<AdminOpLog> {
	void deleteByEndTime(Map<String,Object> params);
}
