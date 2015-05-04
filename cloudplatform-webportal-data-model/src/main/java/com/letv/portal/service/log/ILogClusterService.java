package com.letv.portal.service.log;

import com.letv.portal.model.log.LogCluster;
import com.letv.portal.service.IBaseService;

public interface ILogClusterService extends IBaseService<LogCluster> {

	Boolean isExistByName(String string);

}
