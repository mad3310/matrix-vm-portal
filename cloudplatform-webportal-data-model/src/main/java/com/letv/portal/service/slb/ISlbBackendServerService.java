package com.letv.portal.service.slb;

import java.util.List;

import com.letv.portal.model.slb.SlbBackendServer;
import com.letv.portal.service.IBaseService;

public interface ISlbBackendServerService extends IBaseService<SlbBackendServer> {

	List<SlbBackendServer> selectBySlbServerId(Long slbServerId);

}
