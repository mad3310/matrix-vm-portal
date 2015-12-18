package com.letv.portal.service.product.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.letv.common.exception.ValidateException;
import com.letv.common.util.ExceptionEmailServiceUtil;
import com.letv.portal.constant.Constant;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.common.UserVo;
import com.letv.portal.model.message.Message;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.letvcloud.BillUserAmountService;
import com.letv.portal.service.letvcloud.BillUserServiceBilling;
import com.letv.portal.service.message.IMessageProxyService;
import com.letv.portal.service.message.SendMsgUtils;
import com.letv.portal.service.operate.IRecentOperateService;
import com.letv.portal.service.order.IOrderSubDetailService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IProductInfoRecordService;
import com.letv.portal.service.subscription.ISubscriptionDetailService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.MessageFormatServiceUtil;
import com.mysql.jdbc.StringUtils;

@Service("hostProductServiceOfNewTransaction")
public class HostProductServiceOfNewTransaction {
	
	private static final Logger logger = LoggerFactory.getLogger(HostProductServiceOfNewTransaction.class);

	@Autowired
	private ISubscriptionService subscriptionService;
	@Autowired
	private ISubscriptionDetailService subscriptionDetailService;
	@Autowired
	private IProductInfoRecordService productInfoRecordService;
	@Autowired
    ExceptionEmailServiceUtil exceptionEmailServiceUtil;
	@Autowired
	private SendMsgUtils sendMessage;
	@Autowired
	private IRecentOperateService recentOperateService;
	@Autowired
	private IMessageProxyService messageProxyService;
	@Autowired
	private MessageFormatServiceUtil messageFormatServiceUtil;
	@Autowired
	private BillUserAmountService billUserAmountService;
	@Autowired
	private BillUserServiceBilling billUserServiceBilling;
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrderSubService orderSubService;
	@Autowired
	private IOrderSubDetailService orderSubDetailService;

	// 服务创建成功后回调
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void vmServiceCallback(List<OrderSub> orderSubs, String region,
			String vmId, String volumeId, String floatingIpId, int index,
			Object userData) {
		List<ProductInfoRecord> records = (List<ProductInfoRecord>) userData;
		if (index > records.size()) {
			throw new ValidateException("服务回调index超出List范围！");
		}

		for (ProductInfoRecord record : records) {
			if (record.getBatch() == index) {
				record.setParams(null);
				record.setInvokeType(null);
				record.setDescn(null);
				if (Constants.PRODUCT_VM == record.getProductId()) {
					record.setInstanceId(region + "_" + vmId);
					productInfoRecordService.updateBySelective(record);
				} else if (Constants.PRODUCT_VOLUME == record.getProductId()
						&& volumeId != null) {
					record.setInstanceId(region + "_" + volumeId);
					productInfoRecordService.updateBySelective(record);
				} else if (Constants.PRODUCT_FLOATINGIP == record
						.getProductId() && floatingIpId != null) {
					record.setInstanceId(region + "_" + floatingIpId);
					productInfoRecordService.updateBySelective(record);
				}
			}
		}

		for (OrderSub orderSub : orderSubs) {
			if (orderSub.getProductInfoRecord().getBatch() == index) {
				orderSub.getSubscription().setValid(1);// 更改该订阅为1-有效
			}
		}

	}

	// 服务创建成功后回调
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void serviceCallback(List<OrderSub> orderSubs, String region,
			String id, int index, Object userData) {
		List<ProductInfoRecord> records = (List<ProductInfoRecord>) userData;
		if (index > records.size()) {
			throw new ValidateException("服务回调index超出List范围！");
		}
		// ①更新商品信息记录表（添加实例ID）
		ProductInfoRecord record = records.get(index);
		record.setParams(null);
		record.setProductId(null);
		record.setInvokeType(null);
		record.setDescn(null);
		record.setInstanceId(region + "_" + id);
		productInfoRecordService.updateBySelective(record);
		// ②更改该条订阅为1-有效
		orderSubs.get(index).getSubscription().setValid(1);
	}
	
	/**
	  * @Title: checkOrderFinished
	  * @Description: 验证该批次服务是否全部回调完成
	  * @param orderSubs
	  * @param successCount
	  * @param failCount void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月20日 下午2:37:43
	  */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void checkOrderFinished(List<OrderSub> orderSubs, int successCount, int failCount, String params, String productType, Map<String, String> idNames){
		Set<Integer> batch = new HashSet<Integer>();
		for (OrderSub orderSub : orderSubs) {
			batch.add(orderSub.getProductInfoRecord().getBatch());
		}
		if(successCount+failCount==batch.size()){
			logger.info(productType+"创建全部回调完成.");
			
			
			Map<String, Object> serviceParams = transResult(params);
			
			BigDecimal succPrice = getValidOrderPrice(orderSubs).divide(new BigDecimal(batch.size())).multiply(new BigDecimal(successCount));
			BigDecimal failPrice = getValidOrderPrice(orderSubs).divide(new BigDecimal(batch.size())).multiply(new BigDecimal(failCount));
			
			try {
				//处理冻结金额(减少成功个数冻结余额，转移失败个数冻结金额到可用余额)
				billUserAmountService.dealFreezeAmount(orderSubs.get(0).getCreateUser(), succPrice, failPrice, (String)serviceParams.get("name"), productType);
				
				//有成功的
				if(succPrice.compareTo(new BigDecimal(0))==1) {
					//更新订阅订单起始时间
					updateSubscriptionAndOrderTime(orderSubs);
					
					SimpleDateFormat df = new SimpleDateFormat("yyyyMM");//设置日期格式
					//生成用户账单。
					billUserServiceBilling.add(orderSubs.get(0).getCreateUser(), orderSubs.get(0).getSubscription().getProductId()+"", orderSubs.get(0).getOrderId(), 
							df.format(new Date()), succPrice.toString());
					
					//服务创建成功后保存服务创建成功通知
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			        Date d = new Date();
			        StringBuffer buffer = new StringBuffer();
			        buffer.append("您在").append(sdf.format(d)).append("购买的").append(successCount).append("台").append(productType).append("已成功创建，详细信息如下:");
			        
			        Map<String, Object> messageModel = new HashMap<String, Object>();
			        messageModel.put("warn", "注意：如不能正常使用，可及时联系运维人员。");
			        messageModel.put("introduce", buffer.toString());
			        if(Constant.OPENSTACK.equals(productType)) {
			        	messageModel.put("isVm", true);
			        } else {
			        	messageModel.put("isVm", false);
			        }

					List<Map<String, Object>> resModelList = new LinkedList<Map<String, Object>>();
					messageModel.put("resList", resModelList);
					
					for(String id : idNames.keySet()) {
						//保存最近操作
				        this.recentOperateService.saveInfo("创建"+productType, idNames.get(id), orderSubs.get(0).getCreateUser(), null);
				        
						Map<String, Object> resModel = new HashMap<String, Object>();
						resModel.put("region", orderSubs.get(0).getSubscription().getBaseRegionName());
						resModel.put("type", orderSubs.get(0).getSubscription().getProductName());
						resModel.put("id", id);
						resModel.put("name", idNames.get(id));
						if(Constant.OPENSTACK.equals(productType)) {
							resModel.put("userName", "root");
							resModel.put("password", serviceParams.get("adminPass"));
						}
						resModelList.add(resModel);
					}
					
					String str = messageFormatServiceUtil.format("message/messageCreateNotice.ftl", messageModel);
			        
			        Message msg = new Message();
			        msg.setMsgTitle(productType+"创建成功");
			        msg.setMsgContent(str);
			        msg.setMsgStatus("0");//未读
			        msg.setMsgType("2");//个人消息
			        msg.setCreatedTime(d);
			        messageProxyService.saveMessage(orderSubs.get(0).getCreateUser(), msg);
			    }
				
				if(failPrice.compareTo(new BigDecimal(0))==1) {
					//发送用户通知
					Long createUser = orderSubs.get(0).getCreateUser();
					UserVo ucUser = this.userService.getUcUserById(createUser);
					if(ucUser !=null && !StringUtils.isNullOrEmpty(ucUser.getMobile()))
					this.sendMessage.sendMessage(ucUser.getMobile(), "尊敬的用户，您购买的云产品创建失败，退回账户"+failPrice+"元，请登录网站lcp.letvcloud.com进行确认！如有问题，可拨打客服电话400-055-6060。");
				}
				
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				StringBuffer sb = new StringBuffer();//发生异常时参数记录
				sb.append("orderNumber=").append(orderSubs.get(0).getOrder().getOrderNumber());
				sb.append("&successCount=").append(successCount);
				sb.append("&failCount=").append(failCount);
				sb.append("&serviceParams=").append(serviceParams.toString());
				sb.append("&productType=").append(productType);
				sb.append("&idNames=").append(idNames);
				this.exceptionEmailServiceUtil.sendExceptionEmail(e, productType+"全部创建完成,回调处理出现异常,需人工处理", 
						orderSubs.get(0).getCreateUser(), sb.toString());
			}
			
		}
	}
	
	/**
	 * @Title: getValidOrderPrice
	 * @Description: 获取有效的价格(优先使用折扣价)
	 * @param order
	 * @return double
	 * @throws
	 * @author lisuxiao
	 * @date 2015年9月17日 下午4:23:46
	 */
	private BigDecimal getValidOrderPrice(List<OrderSub> orderSubs) {
		BigDecimal price = new BigDecimal(0);
		for (OrderSub orderSub : orderSubs) {
			if ((orderSub.getDiscountPrice() == null)
					|| (orderSub.getDiscountPrice().compareTo(price)<0)) {// 使用原价
				price = orderSub.getPrice().add(price);
			} else {
				price = orderSub.getDiscountPrice().add(price);// 使用折扣价
			}
		}
		return price;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> transResult(String result) {
		ObjectMapper resultMapper = new ObjectMapper();
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		if (StringUtils.isNullOrEmpty(result))
			return jsonResult;
		try {
			jsonResult = (Map<String, Object>) resultMapper.readValue(result,
					Map.class);
		} catch (Exception e) {
			logger.error("transResult had error:", e);
		}
		return jsonResult;
	}
	
	
	private void updateSubscriptionAndOrderTime(List<OrderSub> orderSubs) {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		Map<String, Object> updateParams = new HashMap<String, Object>();
		updateParams.put("startTime", date);
		for (OrderSub orderSub : orderSubs) {
			Subscription subscription = new Subscription();
			subscription.setId(orderSub.getSubscriptionId());
			subscription.setStartTime(date);
			cal.setTimeInMillis(date.getTime());
			cal.add(Calendar.MONTH, orderSub.getSubscription().getOrderTime());
			cal.set(Calendar.HOUR_OF_DAY, 0);
	        cal.set(Calendar.MINUTE, 0);
	        cal.set(Calendar.SECOND, 0);
	        cal.set(Calendar.MILLISECOND, 0);
	        cal.add(Calendar.DAY_OF_MONTH, 1);
			subscription.setEndTime(cal.getTime());
			if(orderSub.getSubscription().getValid()!=null) {
				subscription.setValid(orderSub.getSubscription().getValid());
			}
			this.subscriptionService.updateBySelective(subscription);
			
			updateParams.put("endTime", cal.getTime());
			updateParams.put("subscriptionId", orderSub.getSubscriptionId());
			this.subscriptionDetailService.updateBuyTime(updateParams);
			
			OrderSub o = new OrderSub();
			o.setId(orderSub.getId());
			o.setStartTime(date);
			o.setEndTime(cal.getTime());
			this.orderSubService.updateBySelective(o);
			updateParams.put("orderSubId", orderSub.getId());
			this.orderSubDetailService.updateBuyTime(updateParams);
		}
	}

}
