package com.letv.portal.service.slb;

import java.util.Map;

import com.letv.portal.model.slb.SlbServer;
import com.letv.portal.service.IBaseService;

public interface ISlbServerService extends IBaseService<SlbServer> {

	Map<String, Object> save(SlbServer slbServer);

}
