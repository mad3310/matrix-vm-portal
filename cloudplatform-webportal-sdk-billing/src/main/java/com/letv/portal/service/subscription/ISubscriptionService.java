package com.letv.portal.service.subscription;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.service.IBaseService;

/**
 * 订阅service
 * @author lisuxiao
 *
 */
public interface ISubscriptionService extends IBaseService<Subscription> {
	/**
	  * @Title: createSubscription
	  * @Description: 生产订阅 
	  * @param id
	  * @param map
	  * @param productInfoRecordId 具体购买产品信息记录ID，用户支付完成后创建服务
	  * @return Subscription 返回订阅   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月6日 下午3:58:11
	  */
	Subscription createSubscription(Long id, Map<String, Object> map, Long productInfoRecordId, Date d, String orderTime, List<BaseStandard> baseStandards);
	Subscription createSubscription(Long id, Map<String, Object> map, Long productInfoRecordId, Date d, String orderTime, Long userId, Integer buyType);
	/**
	  * @Title: selectValidSubscription
	  * @Description: 获取有效的订阅信息
	  * @return List<Subscription>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月21日 下午2:53:26
	  */
	List<Subscription> selectValidSubscription();
	/**
	  * @Title: serviceWarn
	  * @Description: 服务提醒  
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月26日 上午11:40:49
	 */
	void serviceWarn();
	
	/**
	  * @Title: updateSubscriptionStateByInstanceId
	  * @Description: 根据服务ID修改订阅状态
	  * @param instanceId void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月6日 下午6:49:43
	  */
	void updateSubscriptionStateByInstanceId(String instanceId);
}
