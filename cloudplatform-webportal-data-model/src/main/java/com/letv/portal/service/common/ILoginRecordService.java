package com.letv.portal.service.common;

import com.letv.portal.enumeration.LoginClient;
import com.letv.portal.model.common.LoginRecordModel;


public interface ILoginRecordService extends IBaseService<LoginRecordModel>{

    void insert(Long userId, String ip, LoginClient client, boolean isSuccess);
}
