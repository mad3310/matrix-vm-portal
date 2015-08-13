package com.letv.portal.service.openstack.resource.manager.impl;

import java.io.Closeable;

public interface ApiRunnable<ApiType extends Closeable, ReturnType> {
	ReturnType run(ApiType api) throws Exception;
}
