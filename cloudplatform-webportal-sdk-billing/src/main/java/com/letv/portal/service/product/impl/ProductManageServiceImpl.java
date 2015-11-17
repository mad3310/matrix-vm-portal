package com.letv.portal.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.order.Order;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.calculate.ICalculateService;
import com.letv.portal.service.calculate.IHostCalculateService;
import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.billing.CheckResult;
import com.letv.portal.service.openstack.billing.ResourceCreateService;
import com.letv.portal.service.openstack.billing.ResourceQueryService;
import com.letv.portal.service.openstack.resource.FlavorResource;
import com.letv.portal.service.openstack.resource.FloatingIpResource;
import com.letv.portal.service.openstack.resource.VMResource;
import com.letv.portal.service.openstack.resource.VolumeResource;
import com.letv.portal.service.openstack.resource.VolumeTypeResource;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IHostProductService;
import com.letv.portal.service.product.IProductInfoRecordService;
import com.letv.portal.service.product.IProductManageService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionDetailService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.SerialNumberUtil;

@Service("productManageService")
public class ProductManageServiceImpl implements IProductManageService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductManageServiceImpl.class);
	
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
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private ICalculateService calculateService;
	@Autowired
	private IHostCalculateService hostCalculateService;
	@Autowired
	private ResourceCreateService resourceCreateService;
	@Autowired
	private ResourceQueryService resourceQueryService;
	
	
	private BigDecimal getOrderTotalPrice(Subscription sub, String orderTime, Map<String, Object> billingParams) {
		for (SubscriptionDetail subscriptionDetail : sub.getSubscriptionDetails()) {
			if(sub.getProductId()==Constants.PRODUCT_VM || sub.getProductId()==Constants.PRODUCT_VOLUME || 
					sub.getProductId()==Constants.PRODUCT_ROUTER || sub.getProductId()==Constants.PRODUCT_FLOATINGIP) {
				subscriptionDetail.setPrice(this.hostCalculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getElementName(), subscriptionDetail.getStandardValue(),
						1, Integer.parseInt(orderTime), (String)billingParams.get(subscriptionDetail.getElementName()+"_type")));
			} else {
				subscriptionDetail.setPrice(this.calculateService.calculateStandardPrice(sub.getProductId(), sub.getBaseRegionId(), subscriptionDetail.getElementName(), subscriptionDetail.getStandardValue(),
						1, Integer.parseInt(orderTime), (String)billingParams.get(subscriptionDetail.getElementName()+"_type")));
			}
		}
		BigDecimal totalPrice = new BigDecimal(0);
		for (SubscriptionDetail subscriptionDetail : sub.getSubscriptionDetails()) {
			totalPrice = subscriptionDetail.getPrice().add(totalPrice);
		}
		return totalPrice;
	}
	
	private void transferParamsDateToCalculate(Map<String, Object> params, Long id, Map<String, Object> billingParams) {
		if(id==Constants.PRODUCT_VM) {//云主机参数转换
			FlavorResource flavor = resourceQueryService.getFlavor(sessionService.getSession().getUserId(), (String)params.get("region"), (String)params.get("flavorId"));
			VolumeTypeResource volume = this.resourceQueryService.getVolumeType(sessionService.getSession().getUserId(), (String)params.get("region"), (String)params.get("volumeTypeId"));
			billingParams.put("os_cpu_ram", flavor.getVcpus()+"_"+flavor.getRam());
			billingParams.put("os_cpu_ram_type", flavor.getVcpus()+"_"+flavor.getRam());
			billingParams.put("os_storage", params.get("volumeSize")+"");
			billingParams.put("os_storage_type", volume.getName()+"");
			billingParams.put("os_broadband", params.get("bandWidth")+"");
			billingParams.put("order_num", params.get("count")+"");
			billingParams.put("order_time", params.get("order_time")+"");
		} else if(id==Constants.PRODUCT_VOLUME) {//云硬盘
			VolumeTypeResource volume = this.resourceQueryService.getVolumeType(sessionService.getSession().getUserId(), (String)params.get("region"), (String)params.get("volumeTypeId"));
			billingParams.put("os_storage", params.get("size")+"");
			billingParams.put("os_storage_type", volume.getName()+"");
			billingParams.put("order_num", params.get("count")+"");
			billingParams.put("order_time", params.get("order_time")+"");
		} else if(id==Constants.PRODUCT_ROUTER) {//路由器
			billingParams.put("os_router", "router");
			billingParams.put("order_num", params.get("count")+"");
			billingParams.put("order_time", params.get("order_time")+"");
		} else if(id==Constants.PRODUCT_FLOATINGIP) {//公网IP
			billingParams.put("os_broadband", params.get("bandWidth")+"");
			billingParams.put("order_num", params.get("count")+"");
			billingParams.put("order_time", params.get("order_time")+"");
		}
	}
	
	/**
	  * @Title: validateParamsDataByServiceProvider
	  * @Description: 去服务提供方验证参数是否合法
	  * @param id
	  * @param params
	  * @return CheckResult   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月20日 上午11:27:46
	  */
	public CheckResult validateParamsDataByServiceProvider(Long id, String params) {
		CheckResult ret = null;
		if(id==Constants.PRODUCT_VM) {//云主机
			ret = this.resourceCreateService.checkVmCreatePara(params);
		} else if(id==Constants.PRODUCT_VOLUME) {//云硬盘
			ret = this.resourceCreateService.checkVolumeCreatePara(params);
		} else if(id==Constants.PRODUCT_ROUTER) {//路由器
			ret = this.resourceCreateService.checkRouterCreatePara(params);
		} else if(id==Constants.PRODUCT_FLOATINGIP) {//公网IP
			ret = this.resourceCreateService.checkFloatingIpCreatePara(params);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean buy(Long id, String paramsData, String displayData, ResultObject obj) {
		
		Map<String, Object> paramsDataMap = JSONObject.parseObject(paramsData, Map.class);
		Map<String, Object> billingParams = new HashMap<String, Object>();
		
		transferParamsDateToCalculate(paramsDataMap, id, billingParams);
		
		Long regionId = productService.getRegionIdByCode((String)paramsDataMap.get("region"));
		if(regionId!=null) {
			billingParams.put("region", regionId+"");
		}
		
		String orderTime = (String)billingParams.get("order_time");
		
		return validateAndBuy(id, billingParams, orderTime, paramsData, displayData, obj);
	}
	
	/**
	  * @Title: validateAndBuy
	  * @Description: 验证参数并进行购买（订单订阅）
	  * @param productId
	  * @param billingParams
	  * @param orderTime
	  * @param paramsData
	  * @param displayData
	  * @param obj
	  * @return boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月11日 上午10:27:56
	  */
	private boolean validateAndBuy(Long productId, Map<String, Object> billingParams, String orderTime, String paramsData, String displayData, ResultObject obj) {
		boolean ret = false;
		if(productId==Constants.PRODUCT_VM) {//云主机走自己的验证和计算
			ret = hostProductService.validateData(Constants.PRODUCT_VM, billingParams) && hostProductService.validateData(Constants.PRODUCT_VOLUME, billingParams)
					&& hostProductService.validateData(Constants.PRODUCT_FLOATINGIP, billingParams);
			if(ret) {
				//调用vm接口查询该产品对应到几个产品
				Set<Class<? extends BillingResource>> pros = resourceCreateService.getResourceTypesOfVmCreatePara(paramsData);
				createSubscriptionAndOrder(pros, productId, billingParams, orderTime, paramsData, displayData, obj);
			}
		} else if(productId==Constants.PRODUCT_VOLUME || 
				productId==Constants.PRODUCT_ROUTER || productId==Constants.PRODUCT_FLOATINGIP) {
			ret = hostProductService.validateData(productId, billingParams);
			if(ret) {
				createSubscriptionAndOrder(null, productId, billingParams, orderTime, paramsData, displayData, obj);
			}
		} else {//规划的通用逻辑
			ret = productService.validateData(productId, billingParams);
			if(ret) {
				createSubscriptionAndOrder(null, productId, billingParams, orderTime, paramsData, displayData, obj);
			}
		}
		
		return ret;
	}

	private void createSubscriptionAndOrder(Set<Class<? extends BillingResource>> pros, Long productId, Map<String, Object> billingParams, String orderTime, String paramsData, String displayData, ResultObject obj) {
		Order o = new Order();
		for(int i=0; i<Integer.parseInt((String)billingParams.get("order_num")); i++) {
			List<Subscription> subs = new ArrayList<Subscription>();
			if(productId==Constants.PRODUCT_VM) {
				if(pros!=null){
					if(pros.contains(VMResource.class)) {
						createFormAndSubscription(Constants.PRODUCT_VM, paramsData, displayData.split(";;")[0], billingParams, orderTime, i==0?1:0, i, subs);
					}
					if(pros.contains(VolumeResource.class)) {
						createFormAndSubscription(Constants.PRODUCT_VOLUME, paramsData, displayData.split(";;")[1], billingParams, orderTime, 0, i, subs);
					}
					if(pros.contains(FloatingIpResource.class)) {
						createFormAndSubscription(Constants.PRODUCT_FLOATINGIP, paramsData, displayData.split(";;")[2], billingParams, orderTime, 0, i, subs);
					}
				}
			} else {
				createFormAndSubscription(productId, paramsData, displayData, billingParams, orderTime, i==0?1:0, i, subs);
			}
			
			if(i==0) {
				//生产总订单
				o.setOrderNumber(SerialNumberUtil.getNumber(2));
				o.setStatus(0);
				o.setCreateUser(sessionService.getSession().getUserId());
				
				this.orderService.insert(o);
				obj.setData(o.getOrderNumber());
			}
			
			for (Subscription sub : subs) {
				BigDecimal totalPrice = getOrderTotalPrice(sub, (String)billingParams.get("order_time"), billingParams);
				
				//生成子订单
				this.orderSubService.createOrder(sub, o.getId(), sub.getSubscriptionDetails(), totalPrice);
			}
			
		}
	}
		
	
	/**
	  * @Title: createFormAndSubscription
	  * @Description: 创建表单记录和订阅
	  * @param productId 产品id
	  * @param paramsData 页面传入参数
	  * @param billingParams 计费参数
	  * @param orderTime 订单时长
	  * @param invokeType 调用类型，1进行调用，0不调用
	  * @param subs void  记录一个订单的多个订阅 
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年11月11日 上午10:04:04
	  */
	private void createFormAndSubscription(Long productId, String paramsData, String displayData, Map<String, Object> billingParams, String orderTime, int invokeType,int batch, List<Subscription> subs) {
		//保存表单信息
		ProductInfoRecord record = new ProductInfoRecord();
		record.setParams(paramsData);
		record.setProductId(productId);
		record.setInvokeType(invokeType+"");
		record.setBatch(batch);
		record.setDescn(displayData);
		record.setCreateUser(sessionService.getSession().getUserId());
		this.productInfoRecordService.insert(record);
		
		//生产订阅
		Subscription sub = this.subscriptionService.createSubscription(productId, billingParams, record.getId(), new Date(), orderTime);
		subs.add(sub);
	}
	
}
