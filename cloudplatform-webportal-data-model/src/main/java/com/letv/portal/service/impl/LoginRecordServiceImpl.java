package com.letv.portal.service.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.common.exception.CommonException;
import com.letv.portal.dao.ILoginRecordDao;
import com.letv.portal.dao.IUserDao;
import com.letv.portal.enumeration.LoginClient;
import com.letv.portal.model.LoginRecordModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.UserVo;
import com.letv.portal.service.ILoginRecordService;
import com.letv.portal.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("loginRecordService")
public class LoginRecordServiceImpl extends BaseServiceImpl<LoginRecordModel> implements ILoginRecordService{

	@Autowired
	private ILoginRecordDao loginRecordDao;

	public LoginRecordServiceImpl() {
		super(LoginRecordModel.class);
	}


	@Override
	public IBaseDao<LoginRecordModel> getDao() {
		return loginRecordDao;
	}


	@Override
	public void insert(Long userId, String ip, LoginClient client, boolean isSuccess) {
		LoginRecordModel record = new LoginRecordModel();
		record.setUserId(userId);
		record.setLoginIp(ip);
		record.setLoginTime(new Date());
		record.setLoginClient(client);
		record.setSuccess(isSuccess);
		super.insert(record);
	}
}
