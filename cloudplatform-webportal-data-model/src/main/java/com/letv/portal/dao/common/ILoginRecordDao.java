package com.letv.portal.dao.common;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.common.LoginRecordModel;
import org.springframework.stereotype.Repository;

@Repository("loginRecordDao")
public interface ILoginRecordDao extends IBaseDao<LoginRecordModel> {
	
	
}
