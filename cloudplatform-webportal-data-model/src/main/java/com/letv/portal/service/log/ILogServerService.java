package com.letv.portal.service.log;

import java.util.Map;

import com.letv.portal.model.log.LogServer;
import com.letv.portal.service.IBaseService;

public interface ILogServerService extends IBaseService<LogServer> {

	Map<String, Object> save(LogServer logServer);
}
