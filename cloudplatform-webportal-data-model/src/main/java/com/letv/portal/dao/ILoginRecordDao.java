package com.letv.portal.dao;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.LoginRecordModel;
import com.letv.portal.model.UserLogin;
import org.springframework.stereotype.Repository;

@Repository("loginRecordDao")
public interface ILoginRecordDao extends IBaseDao<LoginRecordModel> {
	
	
}
