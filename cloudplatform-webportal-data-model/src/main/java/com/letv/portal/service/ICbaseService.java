package com.letv.portal.service;

import com.letv.portal.model.cbase.CbaseClusterMode;


public interface ICbaseService extends IBaseService<CbaseClusterMode>{

	public String getHello(String name);
	public String getClusterDetail();
	public String getNodeDetail();
	public String getBucketDetail();
	
	//public String createBucket(String name, String ramQuotaMB, String bucketType);
	
}
