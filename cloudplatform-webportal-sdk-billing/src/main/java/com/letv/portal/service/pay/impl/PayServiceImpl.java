package com.letv.portal.service.pay.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.CalendarUtil;
import com.letv.common.util.HttpClient;
import com.letv.common.util.MD5;
import com.letv.portal.constant.Constant;
import com.letv.portal.constant.Constants;
import com.letv.portal.model.common.UserVo;
import com.letv.portal.model.letvcloud.BillUserAmount;
import com.letv.portal.model.order.Order;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.service.common.IUserService;
import com.letv.portal.service.driver.cloudvm.CloudvmResourceInfoService;
import com.letv.portal.service.letvcloud.BillUserAmountService;
import com.letv.portal.service.letvcloud.BillUserServiceBilling;
import com.letv.portal.service.message.SendMsgUtils;
import com.letv.portal.service.openstack.billing.CheckResult;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.pay.IPayService;
import com.letv.portal.service.product.IHostProductService;
import com.letv.portal.service.product.IProductManageService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.SerialNumberUtil;
import com.mysql.jdbc.StringUtils;

@Service("payService")
public class PayServiceImpl implements IPayService {

	private static final Logger logger = LoggerFactory
			.getLogger(PayServiceImpl.class);

	@Autowired
	private IOrderService orderService;
	@Autowired
	private IOrderSubService orderSubService;
	@Autowired
	private BillUserAmountService billUserAmountService;
	@Autowired
	private BillUserServiceBilling billUserServiceBilling;

	@Autowired(required = false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ISubscriptionService subscriptionService;
	@Autowired
	private SendMsgUtils sendMessage;
	@Autowired
	private CloudvmResourceInfoService cloudvmResourceInfoService;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	@Autowired
	private IProductService productService;
	@Autowired
    private TaskExecutor threadPoolTaskExecutor;
	@Autowired
	IProductManageService productManageService;
	@Autowired
	PayServiceOfNewTransaction payServiceOfNewTransaction;
	@Autowired
	IHostProductService hostProductService;

	@Value("${pay.callback}")
	private String PAY_CALLBACK;

	@Value("${pay.success}")
	private String PAY_SUCCESS;
	
	private boolean validateServiceResource(List<OrderSub> orderSubs, Map<String, Object> ret) {
		for (OrderSub orderSub : orderSubs) {
			if(orderSub.getSubscription().getBuyType()==0 && "1".equals(orderSub.getProductInfoRecord().getInvokeType())) {
				CheckResult validateResult = productManageService.validateParamsDataByServiceProvider(orderSub.getSubscription().getProductId(), 
						orderSub.getProductInfoRecord().getParams());
				if(!validateResult.isSuccess()) {
					logger.info("服务接口提供方验证失败：{}", validateResult.getFailureReason());
					ret.put("alert", validateResult.getFailureReason());
					return false;
				}
			}
		}
		return true;
	}
	
	private boolean validateUserAccount(String userMoney, BigDecimal orderPrice, Map<String, Object> ret) {
		boolean b = true;
		BigDecimal accountMoney = new BigDecimal(0);
		try {
			accountMoney = new BigDecimal(userMoney);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			ret.put("alert", "传入金额不合法!");
			b = false;
		}
		if(accountMoney.doubleValue()>0) {
			
			//验证账户金额>=accountaccountMoney
			BillUserAmount userAmount = this.billUserAmountService.getUserAmount(this.sessionService.getSession().getUserId());
			if(userAmount.getAvailableAmount().compareTo(accountMoney)==-1) {
				ret.put("alert", "账户余额小于传入金额!");
				b = false;
			}
			
			//需要支付的金额=总价-账户所选余额
			if(orderPrice.subtract(accountMoney).doubleValue()<0) {
				ret.put("alert", "传入金额不合法!");
				b = false;
			}
		}
		return b;
	}
	
	private Map<String, Object> payAllByUserAccount(List<OrderSub> orderSubs, String orderNumber, BigDecimal useAccountPrice) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Long createUser = orderSubs.get(0).getCreateUser();
		UserVo ucUser = this.userService.getUcUserById(createUser);
		
		if(orderSubs.get(0).getSubscription().getBuyType()==1) {//续费
			reNewOperate(orderSubs, getValidOrderPrice(orderSubs));
			updateOrderPayInfo(orderSubs.get(0).getOrderId(), SerialNumberUtil.getNumber(3), new Date(), 2, useAccountPrice);
			ret.put("responseUrl", this.PAY_SUCCESS + "/" + orderNumber);
			ret.put("response", true);
			//发送用户通知
			if(ucUser !=null && !StringUtils.isNullOrEmpty(ucUser.getMobile())) {
				this.sendMessage.sendMessage(ucUser.getMobile(), "尊敬的用户，您成功续费"+useAccountPrice+"元，请登录网站lcp.letvcloud.com进行体验！如有问题，可拨打客服电话400-055-6060。");
			}
			return ret;
		}
		//设置用户支付部分余额为冻结余额
		if(!this.billUserAmountService.updateUserAmountFromAvailableToFreeze(orderSubs.get(0).getCreateUser(), getValidOrderPrice(orderSubs))) {
			ret.put("alert", "用户可使用余额不足");
			return ret;
		}
		//3代表订单支付金额为0或订单金额全部使用账户余额支付时流水编号自己生成
		if(updateOrderPayInfo(orderSubs.get(0).getOrderId(), SerialNumberUtil.getNumber(3), new Date(), 2, useAccountPrice)) {
			//创建应用实例
			createInstance(orderSubs);
			ret.put("responseUrl", this.PAY_SUCCESS + "/" + orderNumber);
			ret.put("response", true);
			
			//发送用户通知
			if(ucUser !=null && !StringUtils.isNullOrEmpty(ucUser.getMobile())) {
				this.sendMessage.sendMessage(ucUser.getMobile(), "尊敬的用户，您购买云产品成功支付"+useAccountPrice+"元，请登录网站lcp.letvcloud.com进行体验！如有问题，可拨打客服电话400-055-6060。");
			}
		}
		return ret;
	}
	
	

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public Map<String, Object> pay(String orderNumber, Map<String, Object> map) {
		logger.debug("订单去支付：{}", orderNumber);
		Map<String, Object> ret = new HashMap<String, Object>();
		
		List<OrderSub> orderSubs = this.orderSubService.selectOrderSubByOrderNumber(orderNumber);
		if (orderSubs == null || orderSubs.size() == 0) {
			ret.put("alert", "参数未查出订单数据,orderNumber=" + orderNumber);
			return ret;
		}
		
		Order order = orderSubs.get(0).getOrder();
		//验证订单状态和去服务提供方验证参数是否合法
		if(!this.payServiceOfNewTransaction.validateOrderStatus(order, ret) || !validateServiceResource(orderSubs, ret)) {
			return ret;
		}
		
		String userMoney = (String)map.get("accountMoney")==null?"0":(String)map.get("accountMoney");
		//订单总金额
		BigDecimal totalPrice = getValidOrderPrice(orderSubs);
		if(!validateUserAccount(userMoney, totalPrice, ret)) {
			return ret;
		}
		//使用账户金额
		BigDecimal useAccountPrice = new BigDecimal(userMoney);
		//微信或支付宝待支付金额
		BigDecimal payPrice = totalPrice.subtract(useAccountPrice);
		
		
		//全部使用余额支付
		if (payPrice.doubleValue() == 0) {
			return payAllByUserAccount(orderSubs, orderNumber, useAccountPrice);
		}
		
		String pattern = (String) map.get("pattern");
		if(pattern==null || (!Constants.ALI_PAY_PATTERN.equals(pattern) && !Constants.WX_PAY_PATTERN.equals(pattern))) {
			ret.put("alert", "传入的支付方式异常，支付方式："+pattern);
			return ret;
		}
		
		//增加用户充值信息
		this.billUserAmountService.recharge(orderSubs.get(0).getCreateUser(), payPrice, orderNumber,Integer.valueOf(pattern));
		
		//充值
		Map<String, String> params = new HashMap<String, String>();
		String url = getParams(order.getOrderNumber(), payPrice, pattern, this.PAY_CALLBACK, this.PAY_SUCCESS + "/" + orderNumber,
				orderSubs.size() == 1 ? orderSubs.get(0).getSubscription().getProductName() : orderSubs.get(0).getSubscription().getProductName()+ "...", 
				orderSubs.size() == 1 ? orderSubs.get(0).getSubscription().getProductDescn() : orderSubs.get(0).getSubscription().getProductDescn()+ "...", null, params);

		if (Constants.ALI_PAY_PATTERN.equals(pattern)) {// 支付宝方法
			if(useAccountPrice!=null) {
				//更新使用账户余额到订单表
				Order account = new Order();
				account.setId(orderSubs.get(0).getOrderId());
				account.setAccountPrice(useAccountPrice);
				this.orderService.updateBySelective(account);
			}
			logger.info("去支付宝支付：userId=" + sessionService.getSession().getUserId() +"交易信息=订单编号：" + order.getOrderNumber()+",价格："+payPrice);
			ret.put("responseUrl", getPayUrl(url, params));
			ret.put("response", true);
		} else if (Constants.WX_PAY_PATTERN.equals(pattern)) {// 微信支付
			logger.info("去微信支付：userId=" + sessionService.getSession().getUserId() +"交易信息=订单编号：" + order.getOrderNumber()+",价格："+payPrice);
			String str = HttpClient.get(getPayUrl(url, params), 6000, 6000);
			ret = transResult(str);
			if(totalPrice.subtract(useAccountPrice).compareTo(new BigDecimal((String) ret.get("price")))==0) {
				if (!updateOrderPayInfo(orderSubs.get(0).getOrderId(), (String) ret.get("ordernumber"), null, null, useAccountPrice)) {
					ret.put("alert", "微信方式支付异常");
				}
			} else {
				ret.put("alert", "微信方式支付异常");
			}
		}
		return ret;
	}
	
	private void reNewOperate(List<OrderSub> orderSubs, BigDecimal price) {
		String productName = this.cloudvmResourceInfoService.getCloudvmResourceNameById(orderSubs.get(0).getCreateUser()
				, orderSubs.get(0).getSubscription().getProductId(), orderSubs.get(0).getProductInfoRecord().getInstanceId().split("_")[0], 
				orderSubs.get(0).getProductInfoRecord().getInstanceId().split("_")[1]);
		Map<Long, String> productInfo = this.productService.getProductInfo();
		String productType = productInfo.get(orderSubs.get(0).getSubscription().getProductId());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Date d = new Date();
		
		//扣除用户余额
		this.billUserAmountService.reduceAvailableAmount(orderSubs.get(0).getCreateUser(), price, org.apache.commons.lang3.StringUtils.isEmpty(productName)?Constant.NO_NAME:productName, 
				productType, sdf.format(d));
		
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM");//设置日期格式
		//生成用户账单。
		billUserServiceBilling.add(orderSubs.get(0).getCreateUser(), orderSubs.get(0).getSubscription().getProductId()+"", orderSubs.get(0).getOrderId(), 
				df.format(d), price.toString());
		
		//更新订阅状态为1-有效
		for (OrderSub orderSub : orderSubs) {
			Subscription subscription = new Subscription();
			subscription.setId(orderSub.getSubscriptionId());
			subscription.setValid(1);
			subscriptionService.updateBySelective(subscription);
		}
		
		//续费成功后发送邮件
	    UserVo user = this.userService.getUcUserById(orderSubs.get(0).getCreateUser());
	    Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("userName", user.getUsername());
		mailMessageModel.put("time", sdf.format(d));
		mailMessageModel.put("productType", productType);
		mailMessageModel.put("productName", org.apache.commons.lang3.StringUtils.isEmpty(productName)?Constant.NO_NAME:productName);
	    
		
		MailMessage mailMessage = new MailMessage(productType+"续费成功",user.getEmail(),productType+"续费成功",
				"product/reNewNotice.ftl",mailMessageModel);
		mailMessage.setHtml(true);
		defaultEmailSender.sendMessage(mailMessage);
		
	}

	private String getParams(String number, BigDecimal price, String pattern,
			String backUrl, String frontUrl, String productName,
			String productDesc, String defaultBank, Map<String, String> params) {
		try {
			params.put("corderid", number);
			params.put("userid", this.sessionService.getSession().getUserId()
					+ "");
			params.put("username", URLEncoder.encode(this.sessionService
					.getSession().getUserName(), "UTF-8"));
			params.put("companyid", "4");
			params.put("deptid", "112");
			params.put("productid", "0");
			params.put("backurl", backUrl);
			params.put("fronturl", frontUrl);
			params.put("price", price.doubleValue()+"");
			params.put("buyType", "0");
			params.put("pid", "0");
			params.put("chargetype", "1");
			params.put("productname", productName==null?"":URLEncoder.encode(productName, "UTF-8"));
			params.put("productdesc", productDesc==null?"":URLEncoder.encode(productDesc, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			logger.error("payService getParams had error :", e);
		}

		if (Constants.ALI_PAY_PATTERN.equals(pattern)) {// 支付宝支付
			//params.put("defaultbank", defaultBank);
			return Constants.PAY_URL;
		}
		if (Constants.WX_PAY_PATTERN.equals(pattern)) {// 微信支付
			return Constants.WX_URL;
		}
		return null;
	}

	private String getPayUrl(String url, Map<String, String> params) {
		StringBuffer sb = new StringBuffer();
		sb.append(url);
		sb.append("?");
		for (Map.Entry<String, String> entry : params.entrySet()) {
			sb.append((String) entry.getKey());
			sb.append("=");
			sb.append((String) entry.getValue());
			sb.append("&");
		}
		return sb.toString();
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

	@Transactional(isolation = Isolation.READ_COMMITTED)
	public boolean callback(Map<String, Object> map) {
		// ①验证必须字段
		if ((map.get("corderid") == null) || (map.get("stat") == null)
				 || (map.get("money") == null)
				 || (map.get("ordernumber") == null)) {
			 return false;
		}

		List<OrderSub> orderSubs = this.orderSubService
				.selectOrderSubByOrderNumberWithOutSession((String) map
						.get("corderid"));
		if (orderSubs == null || orderSubs.size() == 0) {
			throw new ValidateException("无法查询到订单，订单编号："
					+ (String) map.get("corderid"));
		}

		// ②支付成功已通知服务器并且order的状态为已支付
		if (Integer.parseInt((String) map.get("stat")) == 1
				&& orderSubs.get(0).getOrder().getStatus() == 2) {
			return true;
		}
		
		BigDecimal totalPrice = getValidOrderPrice(orderSubs);

		// ③验证请求是否合法，规则：corderid=xxx&key&money=xxx&companyid=4
		String sign = getSign("4", Constants.SIGN_KEY, orderSubs.get(0)
				.getOrder().getOrderNumber(), totalPrice.subtract(orderSubs.get(0).getOrder().getAccountPrice()).doubleValue()+"");
		if (sign != null && sign.equals(map.get("sign"))) {
			
			if (updateOrderPayInfo(orderSubs.get(0).getOrderId(), (String) map.get("ordernumber"), CalendarUtil.parseCalendar((String)map.get("paytime")).getTime(), 2, null)) {
				
				//更改用户充值信息
				//this.billUserAmountService.rechargeSuccess(orderSubs.get(0).getCreateUser(), order.getOrderNumber(), (String) map.get("ordernumber"), new BigDecimal((String) map.get("money")),false);
				this.billUserAmountService.rechargeSuccessByOrderCode(orderSubs.get(0).getCreateUser(), orderSubs.get(0).getOrder().getOrderNumber(), (String) map.get("ordernumber"), new BigDecimal((String) map.get("money")));
				
				Long createUser = orderSubs.get(0).getCreateUser();
				UserVo ucUser = this.userService.getUcUserById(createUser);
				
				if(orderSubs.get(0).getSubscription().getBuyType()==1) {//续费
					//扣除用户余额
					reNewOperate(orderSubs, totalPrice);
					//发送用户通知
					if(ucUser !=null && !StringUtils.isNullOrEmpty(ucUser.getMobile())) {
						this.sendMessage.sendMessage(ucUser.getMobile(), "尊敬的用户，您成功续费"+totalPrice+"元，请登录网站lcp.letvcloud.com进行体验！如有问题，可拨打客服电话400-055-6060。");
					}
				} else {
					//设置用户支付部分余额为冻结余额
					if(!this.billUserAmountService.updateUserAmountFromAvailableToFreeze(orderSubs.get(0).getCreateUser(), totalPrice)) {
						return false;
					}
					
					//发送用户通知
					if(ucUser !=null && !StringUtils.isNullOrEmpty(ucUser.getMobile())) {
						this.sendMessage.sendMessage(ucUser.getMobile(), "尊敬的用户，您购买云产品成功支付"+totalPrice+"元，请登录网站lcp.letvcloud.com进行体验！如有问题，可拨打客服电话400-055-6060。");
					}
					
					// ④创建应用实例
					createInstance(orderSubs);
				}
				
				return true;
			}
		}
		return false;
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

	private boolean updateOrderPayInfo(long orderId, String orderNumber, Date payTime, Integer status, BigDecimal accountMoney) {
		Order o = new Order();
		o.setId(orderId);
		o.setStatus(status);
		o.setUpdateTime(new Timestamp(new Date().getTime()));
		o.setPayNumber(orderNumber);
		o.setPayTime(payTime);
		if(accountMoney!=null) {
			o.setAccountPrice(accountMoney);
		}
		this.orderService.updateBySelective(o);
		return true;
	}

	public Map<String, Object> queryState(String orderNumber) {
		List<OrderSub> orderSubs = this.orderSubService
				.selectOrderSubByOrderNumber(orderNumber);
		if (orderSubs == null || orderSubs.size() == 0) {
			return null;
		}
		Order order = orderSubs.get(0).getOrder();
		if (order == null || order.getPayNumber() == null) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("companyid", "4");
		params.put("corderid", order.getPayNumber());
		params.put("sign",
				getSign("4", Constants.SIGN_KEY, order.getPayNumber(), null));
		String url = getPayUrl(Constants.QUERY_URL, params);
		String ret = HttpClient.get(url, 2000, 2000);
		Map<String, Object> map = transResult(ret);

		if ((map.get("status") != null) && ((Integer) map.get("status") == 1 || (Integer) map.get("status") == 2)) {// 支付成功
			if(getValidOrderPrice(orderSubs).subtract(orderSubs.get(0).getOrder().getAccountPrice()).compareTo(new BigDecimal((String) map.get("money")))==0) {
				return map;
			}
		}
		return null;
	}

	private String getSign(String companyId, String key, String payNumber,
			String money) {
		if ((companyId == null) || (key == null) || (payNumber == null)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("corderid=");
		sb.append(payNumber);
		sb.append("&");
		sb.append(key);
		if (money != null) {
			sb.append("&money=");
			sb.append(money);
		}
		sb.append("&companyid=");
		sb.append(companyId);
		MD5 m = new MD5();
		return m.getMD5ofStr(sb.toString()).toLowerCase();
	}

	private void createInstance(final List<OrderSub> orderSubs) {
		final List<ProductInfoRecord> records = new ArrayList<ProductInfoRecord>();
		for (OrderSub orders : orderSubs) {
			records.add(orders.getProductInfoRecord());
		}
		for (final OrderSub orderSub : orderSubs) {
			this.threadPoolTaskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					// 进行服务创建
					if ("1".equals(orderSub.getProductInfoRecord().getInvokeType())) {
						logger.debug("调用服务创建：{}-{}", orderSub.getSubscription().getProductId(), orderSub.getProductInfoRecord().getParams());
						if (orderSub.getSubscription().getProductId() == Constants.PRODUCT_VM) {//云主机
							hostProductService.createVm(orderSubs, orderSub.getProductInfoRecord().getParams(), records);
						} else if(orderSub.getSubscription().getProductId() == Constants.PRODUCT_VOLUME) {//云硬盘
							hostProductService.createVolume(orderSubs, orderSub.getProductInfoRecord().getParams(), records);
						} else if(orderSub.getSubscription().getProductId() == Constants.PRODUCT_FLOATINGIP) {//公网IP
							hostProductService.createFloatingIp(orderSubs, orderSub.getProductInfoRecord().getParams(), records);
						} else if(orderSub.getSubscription().getProductId() == Constants.PRODUCT_ROUTER) {//路由
							hostProductService.createRouter(orderSubs, orderSub.getProductInfoRecord().getParams(), records);
						}
					}
				}
			});
			
		}
	}
	

}
