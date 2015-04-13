package com.letv.portal.proxy;

import com.letv.portal.model.gce.GceServer;

public interface IGceProxy extends IBaseProxy<GceServer> {
	
	public void saveAndBuild(GceServer gceServer);
	public void start(Long id);
	public void stop(Long id);
	public void restart(Long id);
}
