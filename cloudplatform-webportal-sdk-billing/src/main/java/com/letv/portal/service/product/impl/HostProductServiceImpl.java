package com.letv.portal.service.product.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.lcp.cloudvm.listener.FloatingIpCreateListener;
import com.letv.lcp.cloudvm.listener.VmCreateListener;
import com.letv.lcp.cloudvm.listener.VolumeCreateListener;
import com.letv.lcp.cloudvm.listener.adapter.FloatingIpCreateAdapter;
import com.letv.lcp.cloudvm.listener.adapter.RouterCreateAdapter;
import com.letv.lcp.cloudvm.listener.adapter.VolumeCreateAdapter;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateEvent;
import com.letv.lcp.cloudvm.model.event.FloatingIpCreateFailEvent;
import com.letv.lcp.cloudvm.model.event.RouterCreateEvent;
import com.letv.lcp.cloudvm.model.event.RouterCreateFailEvent;
import com.letv.lcp.cloudvm.model.event.VmCreateEvent;
import com.letv.lcp.cloudvm.model.event.VmCreateFailEvent;
import com.letv.lcp.cloudvm.model.event.VolumeCreateEvent;
import com.letv.lcp.cloudvm.model.event.VolumeCreateFailEvent;
import com.letv.lcp.cloudvm.model.task.VmCreateContext;
import com.letv.lcp.cloudvm.service.listener.ListenerManagerService;
import com.letv.lcp.openstack.service.billing.IResourceCreateService;
import com.letv.portal.constant.Constant;
import com.letv.portal.dao.base.IBaseStandardDao;
import com.letv.portal.dao.product.IProductElementDao;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.ProductInfoRecord;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IHostProductService;
import com.letv.portal.service.task.ITaskEngine;

@Service("hostProductService")
public class HostProductServiceImpl extends ProductServiceImpl implements IHostProductService {
	
	private final static Logger logger = LoggerFactory.getLogger(HostProductServiceImpl.class);
	
	@Autowired
	private IProductElementDao productElementDao;
	@Autowired
	private IBaseStandardDao baseStandardDao;
	@Autowired
	private IResourceCreateService resourceCreateService;
	@Autowired
	private HostProductServiceOfNewTransaction hostProductServiceOfNewTransaction;
	@Autowired
	private ITaskEngine taskEngine;
	@Autowired
	private ListenerManagerService listenerManagerService;
	@Autowired
	private IOrderSubService orderSubService;
	
	@Override
	public boolean validateData(Long id, Map<String, Object> map) {
		return validateData(id, map, null);
	}
	
	/**
	  * @Title: validateData
	  * @Description: 通用逻辑调用父类验证,云主机独有逻辑自己验证
	  * @param id
	  * @param map
	  * @return boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月1日 下午4:23:04
	  */
	@Override
	public boolean validateData(Long id, Map<String, Object> map, List<BaseStandard> baseStandards) {
		//**********验证产品在该地域是否存在start******************
		if(!super.validateRegion(id, map)) {
			return false;
		}
		//**********验证产品在该地域是否存在end******************
		
		Map<String, List<Map<String, String>>> elements = new HashMap<String, List<Map<String, String>>>();
		Map<String, String> chargeTypes = new HashMap<String, String>();
		
		if(baseStandards==null || baseStandards.size()==0) {
			super.getStandardsInfoByProductElements(id, map, elements, chargeTypes);
		} else {
			super.getStandardsInfoByProductElements(map, elements, chargeTypes, baseStandards);
		}
		
		for (String element : elements.keySet()) {
			if(chargeTypes.get(element)==null) {
				continue;
			}
			if(Integer.parseInt((String)chargeTypes.get(element))==0) {//基础定价
				if(!super.validateChargeTypeZero(map, element, elements)) {
					return false;
				}
			} else if(Integer.parseInt((String)chargeTypes.get(element))==1 || Integer.parseInt((String)chargeTypes.get(element))==2) {//阶梯定价/线性定价
				if(!super.validateChargeTypeOneOrTwo(map, element, elements)) {
					return false;
				}
			} else if(Integer.parseInt((String)chargeTypes.get(element))==3) {
				if(!validateChargeTypeThree(map, element, elements)) {
					return false;
				}
			}
		}
		
		//**********验证购买产品数量和时长是否在规定范围内start******************
		if(!super.validateOrderNum(map, elements) || !super.validateOrderTime(map)) {
			return false;
		}
		//**********验证购买产品数量和时长是否在规定范围内end******************
		return true;
	}

	protected boolean validateChargeTypeThree(Map<String, Object> map, String element, Map<String, List<Map<String, String>>> elements) {
		if(map.get(element)!=null) {
			List<Map<String, String>> ls = elements.get(element);
			for (Map<String, String> map2 : ls) {
				if(map.get(element+"_type")!=null ? map.get(element+"_type").equals(map2.get("type")) && Double.parseDouble((String)map.get(element))>=0
						&& Double.parseDouble((String)map.get(element))<=Double.parseDouble((String)map2.get("value")) : Double.parseDouble((String)map.get(element))>=0
						&& Double.parseDouble((String)map.get(element))<=Double.parseDouble((String)map2.get("value"))) {
					return true;
				}
			}
		}
		logger.info("validateData, map "+element+" element is :"+map.get(element)+", element not within range");
		return false;
	}

	//创建云主机
	@Override
	public void createVm(final List<OrderSub> orderSubs, final String params, final List<ProductInfoRecord> records) {
		logger.info("开始创建云主机！");
		
		Map<String, Object> createInfo = new HashMap<String, Object>();
		createInfo.put("userId", orderSubs.get(0).getCreateUser()+""); 
		createInfo.put("vmCreateConf", params); 
		createInfo.put("orderNumber", orderSubs.get(0).getOrder().getOrderNumber()); 
		
		//添加监听器
		this.listenerManagerService.addListener(orderSubs.get(0).getOrder().getOrderNumber(), new VmCreateListener() {
			
			@Override
			public void vmCreated(VmCreateEvent event) throws Exception {
				List<OrderSub> orderSubsInner = orderSubService.selectOrderSubByOrderNumberWithOutSession((String)event.getUserData());
				List<ProductInfoRecord> recordsInner = new ArrayList<ProductInfoRecord>();
				for (OrderSub orders : orderSubsInner) {
					recordsInner.add(orders.getProductInfoRecord());
				}
				AtomicInteger successCount = new AtomicInteger();
				AtomicInteger failCount = new AtomicInteger();
				Map<String, String> idNames = new HashMap<String, String>();
				for(int i=0; i<event.getContexts().size(); i++ ) {
					VmCreateContext context = event.getContexts().get(i);
					if(context.getServerInstanceId()!=null) {
						logger.info("云主机创建成功回调! num="+i);
						successCount.incrementAndGet();
						idNames.put(context.getServerInstanceId(), context.getResourceName());
						hostProductServiceOfNewTransaction.vmServiceCallback(orderSubsInner, event.getRegion(), context.getServerInstanceId(), context.getVolumeInstanceId(), context.getFloatingIpInstanceId(), i, recordsInner);
						hostProductServiceOfNewTransaction.checkOrderFinished(orderSubsInner, successCount.get(), failCount.get(), params, Constant.OPENSTACK, idNames);
					} else {
						logger.info("云主机创建失败回调! num="+event.getVmIndex());
						failCount.incrementAndGet();
						hostProductServiceOfNewTransaction.checkOrderFinished(orderSubsInner, successCount.get(), failCount.get(), params, Constant.OPENSTACK, idNames);
					}
				}
				
			}
			
			@Override
			public void vmCreateFailed(VmCreateFailEvent event) throws Exception {
				
			}
		});
		
		this.taskEngine.run("LCP_VM_CREATE", createInfo);
		
		
		
//		this.resourceCreateService.createVm(orderSubs.get(0).getCreateUser(), params, new VmCreateAdapter() {
//			private AtomicInteger successCount = new AtomicInteger();
//			private AtomicInteger failCount = new AtomicInteger();
//			private Map<String, String> idNames = new HashMap<String, String>();
//
//			@Override
//			public void vmCreated(VmCreateEvent event) throws Exception {
//				logger.info("云主机创建成功回调! num="+event.getVmIndex());
//				successCount.incrementAndGet();
//				idNames.put(event.getVmId(), event.getName());
//				hostProductServiceOfNewTransaction.vmServiceCallback(orderSubs, event.getRegion(), event.getVmId(), event.getVolumeId(), event.getFloatingIpId(), event.getVmIndex(), event.getUserData());
//				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.OPENSTACK, idNames);
//			}
//
//			@Override
//			public void vmCreateFailed(VmCreateFailEvent event) throws Exception {
//				logger.info("云主机创建失败回调! num="+event.getVmIndex());
//				failCount.incrementAndGet();
//				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.OPENSTACK, idNames);
//			}
//		}, records);
		logger.info("调用创建云主机成功!");
	}
	
	//创建云硬盘
	@Override
	public void createVolume(final List<OrderSub> orderSubs, final String params, List<ProductInfoRecord> records) {
		logger.info("开始创建云硬盘！");
		
		Map<String, Object> createInfo = new HashMap<String, Object>();
		createInfo.put("userId", orderSubs.get(0).getCreateUser()+""); 
		createInfo.put("volumeCreateConf", params); 
		createInfo.put("orderNumber", orderSubs.get(0).getOrder().getOrderNumber()); 
		
		//添加监听器
		this.listenerManagerService.addListener(orderSubs.get(0).getOrder().getOrderNumber(), new VolumeCreateListener() {
			
			@Override
			public void volumeCreated(VolumeCreateEvent event) throws Exception {
				List<OrderSub> orderSubsInner = orderSubService.selectOrderSubByOrderNumberWithOutSession((String)event.getUserData());
				List<ProductInfoRecord> recordsInner = new ArrayList<ProductInfoRecord>();
				for (OrderSub orders : orderSubsInner) {
					recordsInner.add(orders.getProductInfoRecord());
				}
				AtomicInteger successCount = new AtomicInteger();
				AtomicInteger failCount = new AtomicInteger();
				Map<String, String> idNames = new HashMap<String, String>();
				for(int i=0; i<event.getContexts().size(); i++ ) {
					VmCreateContext context = event.getContexts().get(i);
					if(context.getVolumeInstanceId()!=null) {
						logger.info("云硬盘创建成功回调! num="+i);
						successCount.incrementAndGet();
						idNames.put(context.getVolumeInstanceId(), context.getResourceName());
						hostProductServiceOfNewTransaction.serviceCallback(orderSubsInner, event.getRegion(), context.getVolumeInstanceId(), i,recordsInner);
						hostProductServiceOfNewTransaction.checkOrderFinished(orderSubsInner, successCount.get(), failCount.get(), params, Constant.VOLUME, idNames);
					} else {
						logger.info("云硬盘创建失败回调! num="+event.getVolumeIndex());
						failCount.incrementAndGet();
						hostProductServiceOfNewTransaction.checkOrderFinished(orderSubsInner, successCount.get(), failCount.get(), params, Constant.VOLUME, idNames);
					}
				}
				
			}
			
			@Override
			public void volumeCreateFailed(VolumeCreateFailEvent event)
					throws Exception {
				
			}
		});
		
		this.taskEngine.run("LCP_STORAGE_CREATE", createInfo);
		
		
//		this.resourceCreateService.createVolume(orderSubs.get(0).getCreateUser(), params, new VolumeCreateAdapter(){
//			private AtomicInteger successCount = new AtomicInteger();
//			private AtomicInteger failCount = new AtomicInteger();
//			private Map<String, String> idNames = new HashMap<String, String>();
//			
//			@Override
//			public void volumeCreated(VolumeCreateEvent event) throws Exception {
//				logger.info("云硬盘创建成功回调! num="+event.getVolumeIndex());
//				successCount.incrementAndGet();
//				idNames.put(event.getVolumeId(), event.getName());
//				hostProductServiceOfNewTransaction.serviceCallback(orderSubs, event.getRegion(), event.getVolumeId(), event.getVolumeIndex(), event.getUserData());
//				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.VOLUME, idNames);
//			}
//			@Override
//			public void volumeCreateFailed(VolumeCreateFailEvent event)
//					throws Exception {
//				logger.info("云硬盘创建失败回调! num="+event.getVolumeIndex());
//				failCount.incrementAndGet();
//				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.VOLUME, idNames);
//			}
//			
//		}, records);
		
		logger.info("调用创建云硬盘成功!");
	}
	
	//创建公网IP
	@Override
	public void createFloatingIp(final List<OrderSub> orderSubs, final String params, List<ProductInfoRecord> records) {
		logger.info("开始创建公网IP！");
		
		Map<String, Object> createInfo = new HashMap<String, Object>();
		createInfo.put("userId", orderSubs.get(0).getCreateUser()+""); 
		createInfo.put("floatingIpCreateConf", params); 
		createInfo.put("orderNumber", orderSubs.get(0).getOrder().getOrderNumber()); 
		
		//添加监听器
		this.listenerManagerService.addListener(orderSubs.get(0).getOrder().getOrderNumber(), new FloatingIpCreateListener() {
			
			@Override
			public void floatingIpCreated(FloatingIpCreateEvent event) throws Exception {
				List<OrderSub> orderSubsInner = orderSubService.selectOrderSubByOrderNumberWithOutSession((String)event.getUserData());
				List<ProductInfoRecord> recordsInner = new ArrayList<ProductInfoRecord>();
				for (OrderSub orders : orderSubsInner) {
					recordsInner.add(orders.getProductInfoRecord());
				}
				AtomicInteger successCount = new AtomicInteger();
				AtomicInteger failCount = new AtomicInteger();
				Map<String, String> idNames = new HashMap<String, String>();
				for(int i=0; i<event.getContexts().size(); i++ ) {
					VmCreateContext context = event.getContexts().get(i);
					if(context.getFloatingIpInstanceId()!=null) {
						logger.info("公网IP创建成功回调! num="+i);
						successCount.incrementAndGet();
						idNames.put(context.getFloatingIpInstanceId(), context.getResourceName());
						hostProductServiceOfNewTransaction.serviceCallback(orderSubsInner, event.getRegion(), context.getFloatingIpInstanceId(), i, recordsInner);
						hostProductServiceOfNewTransaction.checkOrderFinished(orderSubsInner, successCount.get(), failCount.get(), params, Constant.FLOATINGIP, idNames);
					} else {
						logger.info("公网IP创建失败回调! num="+i);
						failCount.incrementAndGet();
						hostProductServiceOfNewTransaction.checkOrderFinished(orderSubsInner, successCount.get(), failCount.get(), params, Constant.FLOATINGIP, idNames);
					}
				}
				
			}
			
			@Override
			public void floatingIpCreateFailed(FloatingIpCreateFailEvent event)
					throws Exception {
			}
		});
		
		this.taskEngine.run("LCP_FLOATINGIP_CREATE", createInfo);
		
		
//		this.resourceCreateService.createFloatingIp(orderSubs.get(0).getCreateUser(), params, new FloatingIpCreateAdapter() {
//			private AtomicInteger successCount = new AtomicInteger();
//			private AtomicInteger failCount = new AtomicInteger();
//			private Map<String, String> idNames = new HashMap<String, String>();
//			
//			@Override
//			public void floatingIpCreated(FloatingIpCreateEvent event) throws Exception {
//				logger.info("公网IP创建成功回调! num="+event.getFloatingIpIndex());
//				successCount.incrementAndGet();
//				idNames.put(event.getFloatingIpId(), event.getName());
//				hostProductServiceOfNewTransaction.serviceCallback(orderSubs, event.getRegion(), event.getFloatingIpId(), event.getFloatingIpIndex(), event.getUserData());
//				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.FLOATINGIP, idNames);
//			}
//			
//			@Override
//			public void floatingIpCreateFailed(FloatingIpCreateFailEvent event)
//					throws Exception {
//				logger.info("公网IP创建失败回调! num="+event.getFloatingIpIndex());
//				failCount.incrementAndGet();
//				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.FLOATINGIP, idNames);
//			}
//			
//		}, records);
		
		logger.info("调用创建公网IP成功!");
	}
	
	//创建路由器
	@Override
	public void createRouter(final List<OrderSub> orderSubs, final String params, List<ProductInfoRecord> records) {
		logger.info("开始创建路由器！");
		this.resourceCreateService.createRouter(orderSubs.get(0).getCreateUser(), params, new RouterCreateAdapter() {
			private AtomicInteger successCount = new AtomicInteger();
			private AtomicInteger failCount = new AtomicInteger();
			private Map<String, String> idNames = new HashMap<String, String>();
			
			@Override
			public void routerCreated(RouterCreateEvent event) throws Exception {
				logger.info("路由器创建成功回调! num="+event.getRouterIndex());
				successCount.incrementAndGet();
				idNames.put(event.getRouterId(), event.getName());
				hostProductServiceOfNewTransaction.serviceCallback(orderSubs, event.getRegion(), event.getRouterId(), event.getRouterIndex(), event.getUserData());
				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.ROUTER, idNames);
			}
			
			@Override
			public void routerCreateFailed(RouterCreateFailEvent event)
					throws Exception {
				logger.info("路由器创建失败回调! num="+event.getRouterIndex());
				failCount.incrementAndGet();
				hostProductServiceOfNewTransaction.checkOrderFinished(orderSubs, successCount.get(), failCount.get(), params, Constant.ROUTER, idNames);
			}
		}, records);
		
		logger.info("调用创建路由器成功!");
	}
	
}
