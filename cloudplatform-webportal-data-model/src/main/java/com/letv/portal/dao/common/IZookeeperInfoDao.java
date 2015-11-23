package com.letv.portal.dao.common;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.common.ZookeeperInfo;

public interface IZookeeperInfoDao extends IBaseDao<ZookeeperInfo> {

	ZookeeperInfo selectMinusedZk();

}
