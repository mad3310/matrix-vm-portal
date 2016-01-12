package com.letv.lcp.cloudvm.service.storage;

import java.util.Map;

import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.storage.StorageModel;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.cloudvm.service.resource.IResourceService;


public interface IStorageService extends IResourceService<StorageModel> {
	/**
	  * @Title: create
	  * @Description: 创建云硬盘
	  * @param userId 用户id
	  * @param storage 硬盘参数
	  * @param listener 回调计费
	  * @param listenerUserData 计费所需参数   
	  * @param params 任务流所需参数   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月31日 下午3:11:22
	  */
	String create(Long userId, VolumeCreateConf storage, VolumeCreateListener listener, 
			Object listenerUserData, Map<String, Object> params);
	
	/**
	  * @Title: rollBackWithCreateVmFail
	  * @Description: 云主机创建中的云硬盘创建失败回滚
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:50:09
	  */
	void rollBackWithCreateVmFail(Map<String, Object> params);
	
	/**
	  * @Title: addVolume
	  * @Description: 云主机创建中添加云硬盘
	  * @param params
	  * @return String   
	  * @throws 
	  * @author lisuxiao
	  * @date 2016年1月11日 下午6:51:19
	  */
	String addVolume(Map<String, Object> params);
}
