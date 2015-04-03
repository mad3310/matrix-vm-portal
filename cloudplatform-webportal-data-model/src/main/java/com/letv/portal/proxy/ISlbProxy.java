package com.letv.portal.proxy;

import com.letv.portal.model.slb.SlbServer;

public interface ISlbProxy extends IBaseProxy<SlbServer> {
	
	public void saveAndBuild(SlbServer slbServer);

	public void restart(Long id);
}
