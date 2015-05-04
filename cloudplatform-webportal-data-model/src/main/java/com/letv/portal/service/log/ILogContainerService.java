package com.letv.portal.service.log;

import java.util.List;

import com.letv.portal.model.log.LogContainer;
import com.letv.portal.service.IBaseService;

public interface ILogContainerService extends IBaseService<LogContainer> {
	
	public List<LogContainer> selectByLogClusterId(Long logClusterId);
}
