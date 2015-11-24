package com.letv.portal.service.subscription.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.email.bean.MailMessage;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.constant.Constants;
import com.letv.portal.dao.subscription.ISubscriptionDao;
import com.letv.portal.dao.subscription.ISubscriptionDetailDao;
import com.letv.portal.enumeration.ProductType;
import com.letv.portal.model.UserVo;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.message.Message;
import com.letv.portal.model.product.ProductElement;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.model.subscription.SubscriptionDetail;
import com.letv.portal.service.IUserService;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.message.IMessageProxyService;
import com.letv.portal.service.openstack.billing.BillingResource;
import com.letv.portal.service.openstack.billing.ResourceDeleteService;
import com.letv.portal.service.openstack.billing.ResourceLocator;
import com.letv.portal.service.openstack.billing.ResourceQueryService;
import com.letv.portal.service.order.IOrderService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.MessageFormatServiceUtil;
import com.letv.portal.util.SerialNumberUtil;
import com.mysql.jdbc.StringUtils;

@Service("subscriptionService")
public class SubscriptionServiceImpl extends BaseServiceImpl<Subscription> implements ISubscriptionService {
	
	@Autowired
	private IProductService productService;
	@Autowired
	private ResourceQueryService resourceQueryService;
	@Autowired
	private ResourceDeleteService resourceDeleteService;
	
	private final static Logger logger = LoggerFactory.getLogger(SubscriptionServiceImpl.class);
	
	public SubscriptionServiceImpl() {
		super(Subscription.class);
	}

	@Autowired
	private ISubscriptionDao subscriptionDao;
	@Autowired
	private ISubscriptionDetailDao subscriptionDetailDao;
	@Autowired
	private IMessageProxyService messageProxyService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IUserService userService;
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	@Autowired
	private IOrderService orderService;
	@Value("${order.expire.day}")
	private String ORDER_EXPIRE;
	@Value("${order.overdue.day}")
	private String ORDER_OVERDUE;
	@Value("${order.delete.day}")
	private String ORDER_DELETE;
	@Autowired
	private MessageFormatServiceUtil messageFormatServiceUtil;

	@Override
	public IBaseDao<Subscription> getDao() {
		return this.subscriptionDao;
	}


	@Override
	public Subscription createSubscription(Long id, Map<String, Object> map, Long productInfoRecordId, Date date, String orderTime, List<BaseStandard> baseStandards) {
		return createSubscription(id, map, productInfoRecordId, date, orderTime, sessionService.getSession().getUserId(), 0, baseStandards);
	}
	
	private Subscription createSubscription(Long id, Map<String, Object> map, Long productInfoRecordId, Date date, String orderTime, Long userId, Integer buyType, List<BaseStandard> baseStandards) {
		Subscription sub = new Subscription();
		sub.setSubscriptionNumber(SerialNumberUtil.getNumber(1));
		sub.setProductId(id);
		sub.setBaseRegionId(Long.parseLong((String)map.get("region")));
		sub.setChargeType(map.get("chargeType")==null?0:Integer.parseInt((String)map.get("chargeType")));
		sub.setBuyType(buyType);//0-新购,1-续费
		sub.setProductInfoRecordId(productInfoRecordId);
		Integer t = Integer.parseInt(orderTime);
		sub.setOrderTime(t);
		sub.setStartTime(date);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MONTH, t);
		sub.setEndTime(cal.getTime());
		sub.setValid(1);
		sub.setUserId(userId);
		sub.setCreateUser(userId);
		sub.setDeleted(false);
		this.subscriptionDao.insert(sub);
		//存放元素，保存订阅详情时不能有重复元素
		Set<String> elementNames = new HashSet<String>();
		for (BaseStandard baseStandard : baseStandards) {
			String key = baseStandard.getBaseElement().getName();
			if("region".equals(key) || key.endsWith("_type") 
					|| "order_num".equals(key) || "order_time".equals(key) || elementNames.contains(key)) {
				continue;
			}
			elementNames.add(key);
			SubscriptionDetail detail = new SubscriptionDetail();
			detail.setSubscriptionId(sub.getId());
			detail.setElementName(key);
			detail.setStandardType((String)map.get(key+"_type"));
			detail.setStandardValue((String)map.get(key));
			detail.setOrderTime(t);
			detail.setStartTime(date);
			detail.setEndTime(cal.getTime());
			detail.setUserId(userId);
			detail.setCreateUser(userId);
			detail.setDeleted(false);
			detail.setValid(true);
			this.subscriptionDetailDao.insert(detail);
			sub.addSubscriptionDetail(detail);
		}
		return sub;
	}
	
	@Override
	public Subscription createSubscription(Long id, Map<String, Object> map, Long productInfoRecordId, Date date, String orderTime, Long userId, Integer buyType) {
		Subscription sub = new Subscription();
		sub.setSubscriptionNumber(SerialNumberUtil.getNumber(1));
		sub.setProductId(id);
		sub.setBaseRegionId(Long.parseLong((String)map.get("region")));
		sub.setChargeType(map.get("chargeType")==null?0:Integer.parseInt((String)map.get("chargeType")));
		sub.setBuyType(buyType);//0-新购,1-续费
		sub.setProductInfoRecordId(productInfoRecordId);
		Integer t = Integer.parseInt(orderTime);
		sub.setOrderTime(t);
		sub.setStartTime(date);
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		cal.add(Calendar.MONTH, t);
		sub.setEndTime(cal.getTime());
		sub.setValid(1);
		sub.setUserId(userId);
		sub.setCreateUser(userId);
		sub.setDeleted(false);
		this.subscriptionDao.insert(sub);
		List<ProductElement> productElements = this.productService.selectByProductIdWithBaseElement(id);
		for (ProductElement productElement : productElements) {
			String key = productElement.getBaseElement().getName();
			if("region".equals(key) || key.endsWith("_type") 
					|| "order_num".equals(key) || "order_time".equals(key)) {
				continue;
			}
			SubscriptionDetail detail = new SubscriptionDetail();
			detail.setSubscriptionId(sub.getId());
			detail.setElementName(key);
			detail.setStandardType((String)map.get(key+"_type"));
			detail.setStandardValue((String)map.get(key));
			detail.setOrderTime(t);
			detail.setStartTime(date);
			detail.setEndTime(cal.getTime());
			detail.setUserId(userId);
			detail.setCreateUser(userId);
			detail.setDeleted(false);
			detail.setValid(true);
			this.subscriptionDetailDao.insert(detail);
			sub.addSubscriptionDetail(detail);
		}
		return sub;
	}


	@Override
	public List<Subscription> selectValidSubscription() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", sessionService.getSession().getUserId());
		params.put("valid", 1);
		params.put("date", new Date());
		return this.subscriptionDao.selectValidSubscription(params);
	}


	@Override
	public void serviceWarn() {
		//订单到期前30天、15天、7天、3天、1天的9:00，分别进行通知
		//欠费后第1、2、3天的9:00，根据通知时间的不同，剩余天数分别为3、2、1、0
		//欠费后第4天的9:00释放
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("valid", 1);
		params.put("chargeType", 0);//包年包月
		List<Subscription> subscriptions = this.subscriptionDao.selectMaxEndTimeSubscription(params);
		Date now = new Date();
		Map<Long, Map<Long, List<Map<String, Object>>>> expires = new HashMap<Long, Map<Long, List<Map<String, Object>>>>();
		Map<Long, Map<Long, List<Map<String, Object>>>> overdues = new HashMap<Long, Map<Long, List<Map<String, Object>>>>();
		Map<Long, Map<Long, List<Map<String, Object>>>> deletes = new HashMap<Long, Map<Long, List<Map<String, Object>>>>();
		for (Subscription subscription : subscriptions) {
			long day = (subscription.getEndTime().getTime()-now.getTime())/(1000*3600*24);
			logger.info(day+":"+subscription.getProductInfoRecord().getParams());
			//day<=ORDER_DELETE天数后删除服务并置订阅状态为无效
			String delete = ORDER_DELETE;
			int deleteDay = Integer.parseInt(delete.substring(ORDER_DELETE.indexOf(",")+1, ORDER_DELETE.lastIndexOf(",")));
			if(day<=deleteDay) {
				deleteService(subscription.getUserId(), subscription);
			}
			if(ORDER_EXPIRE.contains(","+day+",")) {
				Map<Long, List<Map<String, Object>>> products = null;
				if(expires.get(subscription.getUserId())==null) {
					products = new HashMap<Long, List<Map<String, Object>>>();
					expires.put(subscription.getUserId(), products);
				} else {
					products = expires.get(subscription.getUserId());
				}
				if(products.get(day)==null) {
					List<Map<String, Object>> names = new ArrayList<Map<String, Object>>();
					products.put(day, names);
				}
				Map<String, Object> instanceInfo = transResult(subscription.getProductInfoRecord().getParams());
				instanceInfo.put("instanceId", subscription.getProductInfoRecord().getInstanceId());
				instanceInfo.put("productName", subscription.getProductName());
				instanceInfo.put("regionName", subscription.getBaseRegionName());
				products.get(day).add(instanceInfo);
			} else if(ORDER_OVERDUE.contains(","+day+",")) {
				Map<Long, List<Map<String, Object>>> products = null;
				if(overdues.get(subscription.getUserId())==null) {
					products = new HashMap<Long, List<Map<String, Object>>>();
					overdues.put(subscription.getUserId(), products);
				} else {
					products = overdues.get(subscription.getUserId());
				}
				if(products.get(day)==null) {
					List<Map<String, Object>> names = new ArrayList<Map<String, Object>>();
					products.put(day, names);
				}
				Map<String, Object> instanceInfo = transResult(subscription.getProductInfoRecord().getParams());
				instanceInfo.put("instanceId", subscription.getProductInfoRecord().getInstanceId());
				instanceInfo.put("productName", subscription.getProductName());
				instanceInfo.put("regionName", subscription.getBaseRegionName());
				Calendar cal = Calendar.getInstance();
				cal.setTime(subscription.getEndTime());
				cal.add(Calendar.DATE, 7);
				cal.add(Calendar.HOUR, 9);
				instanceInfo.put("deleteTime", sdf.format(cal.getTime()));
				products.get(day).add(instanceInfo);
			} else if(ORDER_DELETE.contains(","+day+",")) {
				Map<Long, List<Map<String, Object>>> products = null;
				if(deletes.get(subscription.getUserId())==null) {
					products = new HashMap<Long, List<Map<String, Object>>>();
					deletes.put(subscription.getUserId(), products);
				} else {
					products = deletes.get(subscription.getUserId());
				}
				if(products.get(day)==null) {
					List<Map<String, Object>> names = new ArrayList<Map<String, Object>>();
					products.put(day, names);
				}
				Map<String, Object> instanceInfo = transResult(subscription.getProductInfoRecord().getParams());
				instanceInfo.put("instanceId", subscription.getProductInfoRecord().getInstanceId());
				instanceInfo.put("productName", subscription.getProductName());
				instanceInfo.put("regionName", subscription.getBaseRegionName());
				products.get(day).add(instanceInfo);
			}
		}
		//保存通知和发送邮件
		for(Long userId : expires.keySet()) {
			saveMessage(userId, expires.get(userId), 1);
			sendEmailsByType(this.userService.getUcUserById(userId), expires.get(userId), 1);
		}
		for(Long userId : overdues.keySet()) {
			saveMessage(userId, overdues.get(userId), 2);
			sendEmailsByType(this.userService.getUcUserById(userId), overdues.get(userId), 2);
		}
		for(Long userId : deletes.keySet()) {
			saveMessage(userId, deletes.get(userId), 3);
			sendEmailsByType(this.userService.getUcUserById(userId), deletes.get(userId), 3);
		}
        
	}
	
	@SuppressWarnings("unchecked")
	private void deleteService(Long userId, Subscription subscription) {
		String[] id = subscription.getProductInfoRecord().getInstanceId().split("_");
		List<ResourceLocator> rls = new ArrayList<ResourceLocator>();
		rls.add(new ResourceLocator().id(id[1]).region(id[0]).
				type((Class<? extends BillingResource>) ProductType.idToType(Constants.SERVICE_PROVIDER_OPENSTACK, subscription.getProductId())));
		//删除服务
		resourceDeleteService.deleteResource(userId, rls);
		//修改订阅为无效
		Subscription sub = new Subscription();
		sub.setId(subscription.getId());
		sub.setValid(0);
		this.subscriptionDao.updateBySelective(sub);
	}
	
	/**
	  * @Title: saveMessage
	  * @Description: 保存提醒消息
	  * @param userId
	  * @param infos void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月27日 下午2:41:55
	  */
	private void saveMessage(Long userId, Map<Long, List<Map<String, Object>>> infos, int type) {
		if(infos.size()!=0) {
			
			Map<String, Object> messageModel = new HashMap<String, Object>();

			List<Map<String, Object>> resModelList = new LinkedList<Map<String, Object>>();
			messageModel.put("resList", resModelList);
			
			for (Long day : infos.keySet()) {
				List<Map<String, Object>> info = infos.get(day);
				for (Map<String, Object> map : info) {
					Map<String, Object> resModel = new HashMap<String, Object>();
					resModel.put("region", map.get("regionName"));
					resModel.put("type", map.get("productName"));
					resModel.put("id", ((String)map.get("instanceId")).split("_")[1]);
					resModel.put("name", map.get("name"));
					resModel.put("day", Math.abs(day));
					resModel.put("deleteTime", map.get("deleteTime"));
					resModelList.add(resModel);
				}
			}

	        Message msg = new Message();
	        String str = null;
	        
	        if(type==1) {
				messageModel.put("warn", "注意：如不及时续费，到期后资源将被暂停使用。");
		        messageModel.put("introduce", "尊敬的用户，云产品到期提醒，详细信息如下:");
		        str = messageFormatServiceUtil.format("message/messageExpireNotice.ftl", messageModel);
		        msg.setMsgTitle("云产品到期提醒");
			} else if(type==2) {
				messageModel.put("warn", "注意：如不及时续费，到期后资源将被释放，无法恢复；如果您不再使用这些资源, 可以主动销毁, 避免再次收到提醒。");
		        messageModel.put("introduce", "尊敬的用户，您的以下资源因欠费已延期使用，请尽快充值：");
		        str = messageFormatServiceUtil.format("message/messageOverdueNotice.ftl", messageModel);
		        msg.setMsgTitle("云主机欠费延期使用");
			} else if(type==3) {
		        messageModel.put("introduce", "尊敬的用户，您的以下资源因欠费被释放：");
		        str = messageFormatServiceUtil.format("message/messageDeleteNotice.ftl", messageModel);
		        msg.setMsgTitle("云主机欠费资源释放");
			}
	       
	        msg.setMsgContent(str);
	        msg.setMsgStatus("0");//未读
	        msg.setMsgType("2");//个人消息
	        Map<String,Object> msgRet = this.messageProxyService.saveMessage(userId, msg);
	        if(!(Boolean) msgRet.get("result")) {
	        	logger.error("保存云产品消息通知失败，失败原因:"+msgRet.get("message"));
	        }
		}
	}
	
	
	/**
	  * @Title: sendEmailsByType
	  * @Description: 根据类型发送邮件
	  * @param user
	  * @param infos
	  * @param type void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月27日 下午2:43:01
	  */
	private void sendEmailsByType(UserVo user, Map<Long, List<Map<String, Object>>> infos, int type) {
		Map<String, Object> mailMessageModel = new HashMap<String, Object>();
		mailMessageModel.put("userName", user.getUsername());

		List<Map<String, Object>> resModelList = new LinkedList<Map<String, Object>>();
		mailMessageModel.put("resList", resModelList);

		for (Long day : infos.keySet()) {
			List<Map<String, Object>> info = infos.get(day);
			for (Map<String, Object> map : info) {
				Map<String, Object> resModel = new HashMap<String, Object>();
				resModel.put("region", map.get("regionName"));
				resModel.put("type", map.get("productName"));
				resModel.put("id", ((String)map.get("instanceId")).split("_")[1]);
				resModel.put("name", map.get("name"));
				resModel.put("day", Math.abs(day));
				resModel.put("deleteTime", map.get("deleteTime"));
				resModelList.add(resModel);
			}
		}
		MailMessage mailMessage = null;
		if(type==1) {//到期提醒
			mailMessage = new MailMessage("云产品到期提醒", user.getEmail(),
					"云产品到期提醒", "product/expireNotice.ftl", mailMessageModel);
		} else if(type==2) {//欠费提醒
			mailMessage = new MailMessage("云产品欠费延期使用", user.getEmail(),
					"云产品欠费延期使用", "product/overdueNotice.ftl", mailMessageModel);
		} else if(type==3) {//资源释放
			mailMessage = new MailMessage("云产品欠费资源释放", user.getEmail(),
					"云产品欠费资源释放", "product/deleteNotice.ftl", mailMessageModel);
		}
		if(mailMessage!=null) {
			mailMessage.setHtml(true);
			this.defaultEmailSender.sendMessage(mailMessage); 
		}
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

	@SuppressWarnings("unchecked")
	@Override
	public <K, V> Page queryPaginationByMap(Page page, Map<K, V> params) {
		super.queryPaginationByMap(page, params);
		List<Subscription> lists = (List<Subscription>) page.getData();
		
		//调用服务查询服务名称
		Map<String, List<ResourceLocator>> products = new HashMap<String, List<ResourceLocator>>();
		Map<String, String>  rets = new HashMap<String, String>();
		List<ResourceLocator> ress = null;
		for (Subscription subscription : lists) {
			Object obj = ProductType.idToType(Constants.SERVICE_PROVIDER_OPENSTACK, subscription.getProductId());
			if(obj==null) {
				continue;
			}
			if(products.containsKey(Constants.SERVICE_PROVIDER_OPENSTACK)) {
				ress = products.get(Constants.SERVICE_PROVIDER_OPENSTACK);
			} else {
				ress = new ArrayList<ResourceLocator>();
				products.put(Constants.SERVICE_PROVIDER_OPENSTACK, ress);
			}
			String[] str = subscription.getProductInfoRecord().getInstanceId().split("_");
			ress.add(new ResourceLocator().id(str[1]).region(str[0]).type((Class<? extends BillingResource>) obj));
		}
		//调用openstack接口
		if(products.get(Constants.SERVICE_PROVIDER_OPENSTACK)!=null) {
			Map<ResourceLocator, BillingResource> re = resourceQueryService.getResources((Long)params.get("userId"), products.get(Constants.SERVICE_PROVIDER_OPENSTACK));
			for (ResourceLocator resourceLocator : re.keySet()) {
				rets.put(resourceLocator.getRegion()+"_"+resourceLocator.getId(), re.get(resourceLocator).getName());
			}
		}
		
		for (Subscription subscription : lists) {
			subscription.setName(rets.get(subscription.getProductInfoRecord().getInstanceId()));
		}
		return page; 
	}


	@Override
	public void updateSubscriptionStateByInstanceId(String instanceId) {
		this.subscriptionDao.updateSubscriptionStateByInstanceId(instanceId);
	}
	

}
