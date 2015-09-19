package com.letv.portal.service.calculate.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.constant.Arithmetic4Double;
import com.letv.portal.dao.base.IBaseStandardDao;
import com.letv.portal.dao.product.IProductElementDao;
import com.letv.portal.dao.product.IProductPriceDao;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.product.ProductElement;
import com.letv.portal.model.product.ProductPrice;
import com.letv.portal.service.calculate.ICalculateService;

@Service("calculateService")
public class CalculateServiceImpl implements ICalculateService {
	
	@Autowired
	private IProductElementDao productElementDao;
	@Autowired
	private IBaseStandardDao baseStandardDao;
	@Autowired
	private IProductPriceDao productPriceDao;
	
	/**
	  * @Title: getBaseStandard
	  * @Description: 根据商品ID获取所有的规格
	  * @param productId
	  * @return List<BaseStandard>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月18日 下午4:43:59
	  */
	protected List<BaseStandard> getBaseStandard(Long productId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		List<ProductElement> productElements = this.productElementDao.selectByMap(params);
		
		List<BaseStandard> baseStandards = new ArrayList<BaseStandard>();
		for (ProductElement productElement : productElements) {
			List<BaseStandard> stand = this.baseStandardDao.selectBaseStandardWithPrice(productElement.getBaseElementId());
			baseStandards.addAll(stand);
		}
		return baseStandards;
	}
	
	/**
	  * @Title: getPriceByTypeOne
	  * @Description: 计算计费类型为0的价格
	  * @param baseStandard
	  * @param standardValue
	  * @param productPrice
	  * @param price
	  * @param set
	  * @return double   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月18日 下午4:45:44
	  */
	protected double getPriceByTypeZero(BaseStandard baseStandard, String standardValue, 
			ProductPrice productPrice, double p, Set<String> set) {
		double price = p;
		if(baseStandard.getValue().equals(standardValue)) {
			set.add(baseStandard.getStandard());
			if(productPrice!=null && productPrice.getPrice()!=null) {
				price = Arithmetic4Double.add(price, productPrice.getPrice());
			} else {
				price = Arithmetic4Double.add(price, baseStandard.getBasePrice().getPrice());
			}
		}
		
		return price;
	}
	/**
	  * @Title: getPriceByTypeOne
	  * @Description: 计算计费类型为1的价格
	  * @param baseStandard
	  * @param standardValue
	  * @param productPrice
	  * @param price
	  * @param set
	  * @return double   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月18日 下午4:54:11
	  */
	protected double getPriceByTypeOne(BaseStandard baseStandard, String standardValue, 
			ProductPrice productPrice, double p, Set<String> set) {
		double price = p;
		
		String[] str = baseStandard.getBasePrice().getAmount().split("-");
		if(Double.parseDouble(str[0])<=Double.parseDouble(standardValue) && Double.parseDouble(str[1])>=Double.parseDouble(standardValue)) {
			set.add(baseStandard.getStandard());
			if(productPrice!=null && productPrice.getPrice()!=null) {
				Double ret = Arithmetic4Double.multi(productPrice.getPrice(), Double.parseDouble(standardValue));
				price = Arithmetic4Double.add(price, ret);
			} else {
				Double ret = Arithmetic4Double.multi(baseStandard.getBasePrice().getPrice(), Double.parseDouble(standardValue));
				price = Arithmetic4Double.add(price, ret);
			}
			
		}
		return price;
	}
	
	/**
	  * @Title: getPriceByTypeTwo
	  * @Description: 计算计费类型为2的价格
	  * @param baseStandard
	  * @param standardValue
	  * @param productPrice
	  * @param price
	  * @param set
	  * @return double   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月18日 下午4:56:05
	  */
	protected double getPriceByTypeTwo(BaseStandard baseStandard, String standardValue, 
			ProductPrice productPrice, double p, Set<String> set) {
		double price = p;
		
		set.add(baseStandard.getStandard());
		if(productPrice!=null && productPrice.getPrice()!=null) {
			Double ret = Arithmetic4Double.multi(productPrice.getPrice(), Double.parseDouble(standardValue));
			price = Arithmetic4Double.add(price, ret);
		} else {
			Double ret = Arithmetic4Double.multi(baseStandard.getBasePrice().getPrice(), Double.parseDouble(standardValue));
			price = Arithmetic4Double.add(price, ret);
		}
		return price;
	}
	
	@Override
	public Double calculatePrice(Long productId, Map<String, Object> map) {
		Double price = 0d;
		
		List<BaseStandard> baseStandards = getBaseStandard(productId);
		
		//记录计算过的standard
		Set<String> set = new HashSet<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("baseRegionId", map.get("region"));
		params.put("used", 1);
		params.put("deleted", 0);
		params.put("date", new Date());
		for (BaseStandard baseStandard : baseStandards) {
			if(set.contains(baseStandard.getStandard())) {
				continue;
			}
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeZero(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeOne(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeTwo(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set);
			}
			
		}
		//购买时间
		price = Arithmetic4Double.multi(price, Double.parseDouble((String)map.get("order_time")));
		//购买台数
		price = Arithmetic4Double.multi(price, Double.parseDouble((String)map.get("order_num")));
		return price;
	}

	@Override
	public Double calculateStandardPrice(Long productId, Long baseRegionId,
			String standardName, String standardValue, Integer orderNum,
			Integer orderTime) {
		Double price = 0d;
		
		List<BaseStandard> baseStandards = this.baseStandardDao.selectBaseStandardWithPriceByStandard(standardName);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		//记录计算过的standard
		Set<String> set = new HashSet<String>();
		params.put("baseRegionId", baseRegionId);
		params.put("used", 1);
		params.put("deleted", 0);
		params.put("date", new Date());
		for (BaseStandard baseStandard : baseStandards) {
			if(set.contains(baseStandard.getStandard())) {
				break;
			}
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeZero(baseStandard, standardValue, productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeOne(baseStandard, standardValue, productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeTwo(baseStandard, standardValue, productPrice, price, set);
			}
			
		}
		
		//购买时间
		price = Arithmetic4Double.multi(price, orderTime);
		//购买台数
		price = Arithmetic4Double.multi(price, orderNum);
		return price;
	}



}
