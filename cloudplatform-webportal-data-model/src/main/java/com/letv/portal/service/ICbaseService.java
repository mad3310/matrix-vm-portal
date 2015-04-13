package com.letv.portal.service;

import com.letv.portal.model.CbaseClusterModel;

/**
 * 
 * @author liyunhui
 *
 */

public interface ICbaseService extends IBaseService<CbaseClusterModel>{

	public String getHello(String name);
	public String getClusterDetail();
	public String getNodeDetail();
	public String getBucketDetail();
	
	//public String createBucket(String name, String ramQuotaMB, String bucketType);
	
}
