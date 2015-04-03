package com.letv.portal.dao.slb;

import java.util.List;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.slb.SlbBackendServer;

public interface ISlbBackendServerDao extends IBaseDao<SlbBackendServer> {

	List<SlbBackendServer> selectBySlbServerId(Long slbServerId);

}
