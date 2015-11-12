package com.letv.portal.service.product.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.dao.base.IBaseRegionDao;
import com.letv.portal.dao.base.IBaseStandardDao;
import com.letv.portal.dao.product.IProductDao;
import com.letv.portal.dao.product.IProductElementDao;
import com.letv.portal.dao.product.IProductRegionDao;
import com.letv.portal.model.base.BaseRegion;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.order.OrderSub;
import com.letv.portal.model.product.Product;
import com.letv.portal.model.product.ProductElement;
import com.letv.portal.model.product.ProductRegion;
import com.letv.portal.model.subscription.Subscription;
import com.letv.portal.service.impl.BaseServiceImpl;
import com.letv.portal.service.order.IOrderSubService;
import com.letv.portal.service.product.IProductService;
import com.letv.portal.service.subscription.ISubscriptionService;
import com.letv.portal.util.ValidateUtil;

@Service("productService")
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {
	
	private final static Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	public ProductServiceImpl() {
		super(Product.class);
	}

	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductElementDao productElementDao;
	@Autowired
	private IBaseStandardDao baseStandardDao;
	@Autowired
	private IBaseRegionDao baseRegionDao;
	@Autowired
	private IProductRegionDao productRegionDao;
	@Autowired
	private ISubscriptionService subscriptionService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	@Autowired
	private IOrderSubService orderSubService;

	@Override
	public IBaseDao<Product> getDao() {
		return this.productDao;
	}

	@Override
	public Map<String, Object> queryProductDetailById(Long id) {
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", id);
		//获取地域信息
		getProductRegion(ret, params);
		//获取产品元素
		getProductElement(ret, params);
		return ret;
	}
	
	/**
	  * @Title: getProductElement
	  * @Description: 根据产品元素获取各元素具体规格
	  * @param ret
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月1日 上午9:45:50
	  */
	private void getProductElement(Map<String, Object> ret, Map<String, Object> params) {
		List<ProductElement> productElements = this.productElementDao.selectByMap(params);
		for (ProductElement productElement : productElements) {
			getChildren(ret, productElement.getBaseElementId(), 0l);
		}
	}
	
	/**
	  * @Title: getChildren
	  * @Description: 获取该规格下所有子规格
	  * @param ret
	  * @param baseStandardId
	  * @param fatherId void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月1日 上午9:46:18
	  */
	@SuppressWarnings("unchecked")
	private void getChildren(Map<String, Object> ret, Long baseStandardId, Long fatherId) {
		Map<String, Object> elements = new HashMap<String, Object>();
		elements.put("baseElementId", baseStandardId);
		elements.put("fatherId", fatherId);
		
		List<BaseStandard> baseStandards = this.baseStandardDao.selectByMap(elements);
			
		for (BaseStandard map2 : baseStandards) {
			List<Map<String, Object>> arrayList = null;
			if(ret.get(map2.getStandard())==null) {
				arrayList = new ArrayList<Map<String, Object>>();
				ret.put(map2.getStandard(), arrayList);
			} else {
				arrayList = (List<Map<String, Object>>) ret.get(map2.getStandard());
			}
			Map<String, Object> baseStandardMap = new HashMap<String, Object>();
			baseStandardMap.put("text", map2.getType());
			baseStandardMap.put("value", map2.getValue());
			if(!StringUtils.isEmpty(map2.getUnit())) {
				baseStandardMap.put("unit", map2.getUnit());
			}
			arrayList.add(baseStandardMap);
			getChildren(baseStandardMap, baseStandardId, map2.getId());
		}
		
	}
	
	/**
	  * @Title: getProductRegion
	  * @Description: 获取产品的地区信息
	  * @param ret
	  * @param params void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年8月31日 下午5:07:29
	  */
	private void getProductRegion(Map<String, Object> ret, Map<String, Object> params) {
		List<ProductRegion> regions = this.productRegionDao.selectByMap(params);
		List<Map<String, Object>> regionsList = new ArrayList<Map<String, Object>>();
		Set<Long> baseRegionIdSet = new HashSet<Long>();
		for (ProductRegion productRegion : regions) {//保存各个地区：北京、上海等
			if(!baseRegionIdSet.contains(productRegion.getBaseRegionId())) {
				baseRegionIdSet.add(productRegion.getBaseRegionId());
				Map<String, Object> regionMap = new HashMap<String, Object>();
				regionMap.put("text", productRegion.getBaseRegion().getName());
				regionMap.put("value", productRegion.getBaseRegionId());
				regionsList.add(regionMap);
			} 
		}
		for(Map<String, Object> regionMap : regionsList) {
			List<Map<String, Object>> areaList = new ArrayList<Map<String, Object>>();
			for (ProductRegion productRegion : regions) {//保存可用区到相应的地区下：酒仙桥、亦庄等
				if(productRegion.getBaseRegionId().equals(regionMap.get("value"))) {
					Map<String, Object> areaMap = new HashMap<String, Object>();
					areaMap.put("text", productRegion.getHcluster().getHclusterNameAlias());
					areaMap.put("value", productRegion.getHclusterId());
					areaList.add(areaMap);
				}
			}
			regionMap.put("area", areaList);
		}
		ret.put("region", regionsList);
	}

	protected boolean validateRegion(Long id, Map<String, Object> map) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", id);
		params.put("baseRegionId", map.get("region")==null?-1:map.get("region"));
		//取消可用区概念了，地域变为可用区，例如：北京1，北京2
		//params.put("hclusterId", map.get("area")==null?-1:map.get("area"));
		List<ProductRegion> regions = this.productRegionDao.selectByMap(params);
		if(regions==null || regions.size()==0) {
			logger.info("validateData, this product not belongs to the region! region : "+map.get("region"));
			return false;
		}
		return true;
	}
	
	/**
	  * @Title: getStandardsInfoByProductElements
	  * @Description: 获取该产品下元素的规格及相应的计费类型
	  * @param id
	  * @param map
	  * @param standards
	  * @param chargeTypes void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月19日 下午3:34:48
	  */
	protected void getStandardsInfoByProductElements(Long id, Map<String, Object> map, 
			Map<String, List<Map<String,String>>> standards, Map<String, String> chargeTypes) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.clear();
		params.put("productId", id);
		List<ProductElement> productElements = this.productElementDao.selectByMap(params);
		for (ProductElement productElement : productElements) {
			params.clear();
			params.put("baseElementId", productElement.getBaseElementId());
			List<BaseStandard> baseStandards = this.baseStandardDao.selectByMap(params);
			for (BaseStandard baseStandard : baseStandards) {
				List<Map<String, String>> list = null;
				Map<String, String> typeAndValue = new HashMap<String, String>();
				typeAndValue.put("type", baseStandard.getType());
				typeAndValue.put("value", baseStandard.getValue());
				if(standards.containsKey(baseStandard.getBaseElement().getName())) {
					list = standards.get(baseStandard.getBaseElement().getName());
					list.add(typeAndValue);
				} else {
					list = new ArrayList<Map<String, String>>();
					list.add(typeAndValue);
					standards.put(baseStandard.getBaseElement().getName(), list);
				}
			}
			if(baseStandards!=null && baseStandards.size()!=0 && baseStandards.get(0).getBasePrice()!=null) {
				chargeTypes.put(baseStandards.get(0).getBaseElement().getName(), baseStandards.get(0).getBasePrice().getType());
			}
		}
	}
	
	protected boolean validateChargeTypeZero(Map<String, Object> map, String element, Map<String, List<Map<String, String>>> elements) {
		if(map.get(element)!=null) {
			List<Map<String, String>> ls = elements.get(element);
			for (Map<String, String> map2 : ls) {
				if(map.get(element+"_type")==null ? map.get(element).equals(map2.get("value")) : 
					map.get(element+"_type").equals(map2.get("type")) && map.get(element).equals(map2.get("value"))) {
					return true;
				}
			}
		}
		logger.info("validateData, map "+element+" element is :"+map.get(element)+", element not within");
		return false;
	}
	protected boolean validateChargeTypeOneOrTwo(Map<String, Object> map, String element, Map<String, List<Map<String, String>>> elements) {
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
	protected boolean validateOrderNum(Map<String, Object> map, Map<String, List<Map<String, String>>> elements) {
		if(map.get("order_num")!=null) {
			List<Map<String, String>> ls = elements.get("order_num");
			for (Map<String, String> map2 : ls) {
				if(ValidateUtil.isNumeric((String)map.get("order_num")) && Double.parseDouble((String)map.get("order_num"))>0
						&& Double.parseDouble((String)map.get("order_num"))<=Double.parseDouble((String)map2.get("value"))) {
					return true;
				}
			}
		}
		logger.info("validateData, map order_num standard is :"+map.get("order_num")+", standard not within range");
		return false;
	}
	protected boolean validateOrderTime(Map<String, Object> map) {
		if(map.get("order_time")==null || !ValidateUtil.isNumeric((String)map.get("order_num")) || Double.parseDouble((String)map.get("order_time"))<1 
				|| (Double.parseDouble((String)map.get("order_time"))>9 && Double.parseDouble((String)map.get("order_time"))!=12 && 
				Double.parseDouble((String)map.get("order_time"))!=24 && Double.parseDouble((String)map.get("order_time"))!=36)) {
			logger.info("validateData, map order_time standard is :"+map.get("order_time")+", standard not within range");
			return false;
		} else {
			if(Double.parseDouble((String)map.get("order_time"))==12) {//一年按10个月
				map.put("order_time", "10");
			} else if(Double.parseDouble((String)map.get("order_time"))==24) {//2年按20个月
				map.put("order_time", "20");
			} else if(Double.parseDouble((String)map.get("order_time"))==36) {//3年按30个月
				map.put("order_time", "30");
			}
		}
		return true;
	}
	
	/**
	  * @Title: validateData
	  * @Description: 验证产品元素值是否合法
	  * @param id
	  * @param map
	  * @return boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月1日 下午4:23:04
	  */
	@Override
	public boolean validateData(Long id, Map<String, Object> map) {
		//**********验证产品在该地域是否存在start******************
		if(!validateRegion(id, map)) {
			return false;
		}
		//**********验证产品在该地域是否存在end******************
		
		Map<String, List<Map<String, String>>> elements = new HashMap<String, List<Map<String, String>>>();
		Map<String, String> chargeTypes = new HashMap<String, String>();
		getStandardsInfoByProductElements(id, map, elements, chargeTypes);
		
		for (String standard : elements.keySet()) {
			if(chargeTypes.get(standard)==null) {
				continue;
			}
			if(Integer.parseInt((String)chargeTypes.get(standard))==0) {//基础定价
				if(!validateChargeTypeZero(map, standard, elements)) {
					return false;
				}
			} else if(Integer.parseInt((String)chargeTypes.get(standard))==1 || Integer.parseInt((String)chargeTypes.get(standard))==2) {//阶梯定价/线性定价
				if(!validateChargeTypeOneOrTwo(map, standard, elements)) {
					return false;
				}
			}
		}
		
		//**********验证购买产品数量和时长是否在规定范围内start******************
		if(!validateOrderNum(map, elements) || !validateOrderTime(map)) {
			return false;
		}
		//**********验证购买产品数量和时长是否在规定范围内end******************
		return true;
	}

	@Override
	public Long getRegionIdByCode(String regionCode) {
		if(regionCode==null) {
			return null;
		}
		//把地域的编号转换为地域的ID
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("code", regionCode);
		List<BaseRegion> regions = this.baseRegionDao.selectByMap(params);
		if(regions!=null && regions.size()>0) {
			return regions.get(0).getId();
		}
		return null;
	}

	@Override
	public Map<Long, BigDecimal> dailyConsume() {
		List<Subscription> subscriptions = this.subscriptionService.selectValidSubscription();
		Map<Long, BigDecimal> ret = new HashMap<Long, BigDecimal>();
		for (Subscription subscription : subscriptions) {
			if(subscription.getChargeType()==0) {//包年包月
				List<OrderSub> orderSubs = this.orderSubService.selectOrderSubBySubscriptionId(subscription.getId());
				BigDecimal price = ret.get(subscription.getProductId());
				if(price==null) {
					price = new BigDecimal(0);
				}
				for (OrderSub orderSub : orderSubs) {
					long day = (orderSub.getEndTime().getTime()-orderSub.getStartTime().getTime())/(1000*3600*24); 
					price = orderSub.getDiscountPrice()==null ? price.add(orderSub.getPrice().divide(new BigDecimal(day), 2, BigDecimal.ROUND_HALF_DOWN)): 
							price.add(orderSub.getDiscountPrice().divide(new BigDecimal(day), 2, BigDecimal.ROUND_HALF_DOWN));
				}
				ret.put(subscription.getProductId(), price);
			} else if(subscription.getChargeType()==1) {//按量TODO
				
			}
		}
		return ret;
	}

	@Override
	public List<ProductElement> selectByProductIdWithBaseElement(Long productId) {
		return this.productElementDao.selectByProductIdWithBaseElement(productId);
	}


}
