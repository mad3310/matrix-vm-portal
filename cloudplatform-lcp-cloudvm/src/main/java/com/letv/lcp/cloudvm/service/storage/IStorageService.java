package com.letv.lcp.cloudvm.service.storage;

import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.model.storage.VolumeCreateConf;
import com.letv.lcp.cloudvm.model.storage.StorageModel;
import com.letv.lcp.cloudvm.service.resource.IResourceService;


public interface IStorageService extends IResourceService<StorageModel> {
	/**
	  * @Title: create
	  * @Description: 创建云硬盘
	  * @param userId 用户id
	  * @param storage 硬盘参数
	  * @param listener 回调计费
	  * @param listenerUserData 计费所需参数   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月31日 下午3:11:22
	  */
	void create(Long userId, VolumeCreateConf storage, VolumeCreateListener listener, Object listenerUserData);
}
