package com.letv.lcp.openstack.service.manage;

import java.io.Closeable;

public interface ApiRunnable<ApiType extends Closeable, ReturnType> {
	ReturnType run(ApiType api) throws Exception;
}
