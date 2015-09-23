package com.letv.portal.controller.billing;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Arithmetic4Double;
import com.letv.portal.model.order.Order;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.calculate.ICalculateService;
import com.letv.portal.service.calculate.IHostCalculateService;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IHostProductService;
import com.letv.portal.service.product.IProductInfoRecordService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionDetailService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.SerialNumberUtil;

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
	IHostProductService hostProductService;
	@Autowired
	IProductInfoRecordService productInfoRecordService;
	@Autowired
	ISubscriptionService subscriptionService;
	@Autowired
	ISubscriptionDetailService subscriptionDetailService;
	@Autowired
	IOrderService orderService;
	@Autowired
	IOrderSubService orderSubService;
	@Autowired
	IDbProxy dbProxy;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private ICalculateService calculateService;
	@Autowired
	private IHostCalculateService hostCalculateService;
	@Autowired
	private ResourceCreateService resourceCreateService;
	
	
	@RequestMapping(value="/product/{id}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject queryProductDetail(@PathVariable Long id, ResultObject obj) {
		obj.setData(this.productService.queryProductDetailById(id));
		return obj;
	}
	
	private boolean validateData(Long id, Map<String, Object> map) {
		if(id==2) {//云主机走自己的验证和计算
			return hostProductService.validateData(id, map);
		} else {//规划的通用逻辑
			return productService.validateData(id, map);
		}
	}
	private double getOrderTotalPrice(Long id, Subscription sub, List<SubscriptionDetail> subDetails, String orderTime) {
		for (SubscriptionDetail subscriptionDetail : subDetails) {
			if(id==2) {
				subscriptionDetail.setPrice(this.hostCalculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getStandardName(), subscriptionDetail.getStandardValue(),
						1, Integer.parseInt(orderTime)));
			} else {
				subscriptionDetail.setPrice(this.calculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getStandardName(), subscriptionDetail.getStandardValue(),
						1, Integer.parseInt(orderTime)));
			}
		}
		Double totalPrice = 0d;
		for (SubscriptionDetail subscriptionDetail : subDetails) {
			totalPrice = Arithmetic4Double.add(totalPrice, subscriptionDetail.getPrice());
		}
		return totalPrice;
	}
	
	private void transferParamsDateToCalculate(Map<String, Object> params, Long id, Map<String, Object> billingParams) {
		if(id==2) {//云主机参数转换
			FlavorResource flavor = resourceCreateService.getFlavor(sessionService.getSession().getUserId(), (String)params.get("region"), (String)params.get("flavorId"));
			billingParams.put("cpu_ram", flavor.getVcpus()+"_"+flavor.getRam());
			billingParams.put("os_storage", params.get("volumeSize")+"");
			billingParams.put("os_broadband", params.get("bandWidth")+"");
			billingParams.put("order_num", params.get("count")+"");
			billingParams.put("order_time", params.get("order_time")+"");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/buy/{id}",method=RequestMethod.POST)   
	public @ResponseBody ResultObject buy(@PathVariable Long id, String paramsData, String displayData, ResultObject obj) {
		Map<String, Object> paramsDataMap = JSONObject.parseObject(paramsData, Map.class);
		Map<String, Object> billingParams = new HashMap<String, Object>();
		
		transferParamsDateToCalculate(paramsDataMap, id, billingParams);
		
		Long regionId = productService.getRegionIdByCode((String)paramsDataMap.get("region"));
		if(regionId!=null) {
			billingParams.put("region", regionId+"");
		}
		
		String orderTime = (String)billingParams.get("order_time");
		
		if(validateData(id, billingParams)) {//验证参数合法性
			Order o = new Order();
			for(int i=0; i<Integer.parseInt((String)billingParams.get("order_num")); i++) {
				//保存表单信息
				ProductInfoRecord record = new ProductInfoRecord();
				record.setParams(paramsData);
				record.setProductType(id+"");
				if(i==0) {
					record.setInvokeType("1");
				} else {
					record.setInvokeType("0");
				}
				record.setCreateUser(sessionService.getSession().getUserId());
				this.productInfoRecordService.insert(record);
				
				//生产订阅
				Subscription sub = this.subscriptionService.createSubscription(id, billingParams, record.getId(), new Date(), orderTime);
				if(sub.getChargeType()==0) {//包年包月,立即生产订单
					if(i==0) {
						//生产总订单
						o.setOrderNumber(SerialNumberUtil.getNumber(2));
						o.setStatus(0);
						o.setCreateUser(sessionService.getSession().getUserId());
						o.setDescn(displayData);
						this.orderService.insert(o);
						obj.setData(o.getOrderNumber());
					}
					
					List<SubscriptionDetail> subDetails = this.subscriptionDetailService.selectByMapAndTime(sub.getId());
					double totalPrice = getOrderTotalPrice(id, sub, subDetails, (String)billingParams.get("order_time"));
					
					//生成子订单
					this.orderSubService.createOrder(sub, o.getId(), subDetails, totalPrice);
				}
			}
		} else {
			obj.setResult(0);
			obj.addMsg("参数合法性验证失败");
		}
		
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
