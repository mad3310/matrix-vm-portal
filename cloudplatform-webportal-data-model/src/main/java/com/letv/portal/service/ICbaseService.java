package com.letv.portal.service;

import com.letv.portal.model.cbase.CbaseClusterModel;


public interface ICbaseService extends IBaseService<CbaseClusterModel>{

	public String getHello(String name);
	public String getClusterDetail();
	public String getNodeDetail();
	public String getBucketDetail();
	
}