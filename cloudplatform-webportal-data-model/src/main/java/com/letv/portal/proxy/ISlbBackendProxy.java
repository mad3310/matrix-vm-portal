package com.letv.portal.proxy;

import com.letv.portal.model.slb.SlbBackendServer;

public interface ISlbBackendProxy extends IBaseProxy<SlbBackendServer> {
	
	public void saveAndConfig(SlbBackendServer slbBackendServer);
}
