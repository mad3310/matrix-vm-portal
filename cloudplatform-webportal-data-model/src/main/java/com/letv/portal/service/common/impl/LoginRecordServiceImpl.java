package com.letv.portal.service.common.impl;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.dao.common.ILoginRecordDao;
import com.letv.portal.enumeration.LoginClient;
import com.letv.portal.model.common.LoginRecordModel;
import com.letv.portal.service.common.ILoginRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


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
