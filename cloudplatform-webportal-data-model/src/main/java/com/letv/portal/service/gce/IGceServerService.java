package com.letv.portal.service.gce;

import java.util.Map;

import com.letv.portal.model.gce.GceServer;
import com.letv.portal.service.IBaseService;

public interface IGceServerService extends IBaseService<GceServer> {

	Map<String, Object> save(GceServer gceServer);
	
}
