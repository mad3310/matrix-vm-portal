package com.letv.portal.controller.billing;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionService;

/**Program Name: BaseProductController <br>
 * Description:  基础产品<br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年7月28日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/billing")
public class ProductController {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	IProductService productService;
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IDbProxy dbProxy;
	
	@RequestMapping(value="/product/{id}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryProductDetail(@PathVariable Long id, ResultObject obj) {
		obj.setData(this.productService.queryProductDetailById(id));
		return obj;
	}
	
//	public @ResponseBody ResultObject save(DbModel dbModel,boolean isCreateAdmin,String calculateData) {
//		Map<String, Object> billingPrams = JSONObject.parseObject(calculateData, Map.class);
//		Long rdsId = this.dbProxy.save(dbModel,isCreateAdmin);
//		ResultObject obj = new ResultObject();
//		Subscription sub = this.subscriptionService.createSubscription(1l, billingPrams, rdsId);
//		if(sub.getChargeType()==0) {
//			Long orderId = this.orderService.createOrder(sub.getId());
//			obj.setData(orderId);
//		}
//		return obj;
//	}
	
	
	
}
