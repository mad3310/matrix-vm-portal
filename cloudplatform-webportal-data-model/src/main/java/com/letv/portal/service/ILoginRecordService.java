package com.letv.portal.service;

import com.letv.portal.enumeration.LoginClient;
import com.letv.portal.model.LoginRecordModel;
import com.letv.portal.model.UserModel;
import com.letv.portal.model.UserVo;

import java.util.Date;


public interface ILoginRecordService extends IBaseService<LoginRecordModel>{

    void insert(Long userId, String ip, LoginClient client, boolean isSuccess);
}
