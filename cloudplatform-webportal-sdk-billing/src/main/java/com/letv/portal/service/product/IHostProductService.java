package com.letv.portal.service.product;

import java.util.List;

import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.ProductInfoRecord;


/**
 * 云主机服务
 * @author lisuxiao
 *
 */
public interface IHostProductService extends IProductService {
	/**
	  * @Title: createVm
	  * @Description: 创建云主机
	  * @param orderSubs 一个订单下所有的子订单
	  * @param params 创建服务的参数
	  * @param records 回调需要处理的数据 
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月16日 下午3:51:10
	  */
	void createVm(final List<OrderSub> orderSubs, final String params, final List<ProductInfoRecord> records);
	/**
	  * @Title: createVolume
	  * @Description: 创建云硬盘
	  * @param orderSubs 一个订单下所有的子订单
	  * @param params 创建服务的参数
	  * @param records 回调需要处理的数据   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月16日 下午3:51:18
	  */
	void createVolume(final List<OrderSub> orderSubs, final String params, List<ProductInfoRecord> records);
	/**
	  * @Title: createFloatingIp
	  * @Description: 创建公网IP
	  * @param orderSubs 一个订单下所有的子订单
	  * @param params 创建服务的参数
	  * @param records 回调需要处理的数据   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月16日 下午3:51:36
	  */
	void createFloatingIp(final List<OrderSub> orderSubs, final String params, List<ProductInfoRecord> records);
	/**
	  * @Title: createRouter
	  * @Description: 创建路由器
	  * @param orderSubs 一个订单下所有的子订单
	  * @param params 创建服务的参数
	  * @param records 回调需要处理的数据   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年12月16日 下午3:51:45
	  */
	void createRouter(final List<OrderSub> orderSubs, final String params, List<ProductInfoRecord> records);
}
