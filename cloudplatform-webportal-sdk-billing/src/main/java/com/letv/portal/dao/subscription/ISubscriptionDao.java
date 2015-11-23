package com.letv.portal.dao.subscription;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.subscription.Subscription;

public interface ISubscriptionDao extends IBaseDao<Subscription> {
	
	List<Subscription> selectValidSubscription(Map<String, Object> params);
	/**
	  * @Title: selectMaxEndTimeSubscription
	  * @Description: 获取用户最晚时间订阅记录
	  * @param params
	  * @return List<Subscription>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月26日 下午2:13:36
	  */
	List<Subscription> selectMaxEndTimeSubscription(Map<String, Object> params);
	
	/**
	  * @Title: updateSubscriptionStateByInstanceId
	  * @Description: 根据实例ID更改订阅状态（为无效）
	  * @param instanceId void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月6日 下午6:51:01
	  */
	void updateSubscriptionStateByInstanceId(String instanceId);

}
