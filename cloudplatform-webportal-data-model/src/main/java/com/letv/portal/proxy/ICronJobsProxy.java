package com.letv.portal.proxy;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.HclusterModel;

public interface ICronJobsProxy  {

	void checkCount();
	void asyncClusterCount(List<Map<String,Object>> data,HclusterModel hcluster);
}
