package com.letv.portal.service.calculate.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	protected BigDecimal getPriceByTypeZero(BaseStandard baseStandard, String standardValue, String standardType,
			ProductPrice productPrice, BigDecimal p, Set<String> set) {
		BigDecimal price = p;
		if(standardType==null ? baseStandard.getValue().equals(standardValue) : 
			baseStandard.getValue().equals(standardValue) && standardType.equals(baseStandard.getType())) {
			set.add(baseStandard.getBaseElement().getName());
			if(productPrice!=null && productPrice.getPrice()!=null) {
				price = productPrice.getPrice().add(price);
			} else {
				price = baseStandard.getBasePrice().getPrice().add(price);
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
	protected BigDecimal getPriceByTypeOne(BaseStandard baseStandard, String standardValue, String standardType,
			ProductPrice productPrice, BigDecimal p, Set<String> set) {
		BigDecimal price = p;
		
		String[] str = baseStandard.getBasePrice().getAmount().split("-");
		if(standardType==null ? 
				Double.parseDouble(str[0])<=Double.parseDouble(standardValue) && Double.parseDouble(str[1])>=Double.parseDouble(standardValue) :
				Double.parseDouble(str[0])<=Double.parseDouble(standardValue) && Double.parseDouble(str[1])>=Double.parseDouble(standardValue) &&
				standardType.equals(baseStandard.getType())) {
			set.add(baseStandard.getBaseElement().getName());
			if(productPrice!=null && productPrice.getPrice()!=null) {
				price = productPrice.getPrice().multiply(new BigDecimal(standardValue)).add(price);
			} else {
				price = baseStandard.getBasePrice().getPrice().multiply(new BigDecimal(standardValue)).add(price);
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
	protected BigDecimal getPriceByTypeTwo(BaseStandard baseStandard, String standardValue, String standardType,
			ProductPrice productPrice, BigDecimal p, Set<String> set) {
		BigDecimal price = p;
		
		if(standardType==null ? true : standardType.equals(baseStandard.getType())) {
			set.add(baseStandard.getBaseElement().getName());
			if(productPrice!=null && productPrice.getPrice()!=null) {
				price = productPrice.getPrice().multiply(new BigDecimal(standardValue)).add(price);
			} else {
				price = baseStandard.getBasePrice().getPrice().multiply(new BigDecimal(standardValue)).add(price);
			}
		}
		return price;
	}
	
	@Override
	public BigDecimal calculatePrice(Long productId, Map<String, Object> map) {
		return calculatePrice(productId, map, null);
	}
	
	@Override
	public BigDecimal calculatePrice(Long productId, Map<String, Object> map, List<BaseStandard> baseStandards) {
		BigDecimal price = new BigDecimal(0);
		
		if(baseStandards==null) {//传入的参数为空，自己根据产品id进行计算
			baseStandards = getBaseStandard(productId);
		}
		
		//记录计算过的standard
		Set<String> set = new HashSet<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		params.put("baseRegionId", map.get("region"));
		params.put("used", 1);
		params.put("deleted", 0);
		params.put("date", new Date());
		for (BaseStandard baseStandard : baseStandards) {
			//该元素已经计算过或该元素不计费
			if(set.contains(baseStandard.getStandard()) || baseStandard.getBasePrice()==null 
					|| baseStandard.getBasePrice().getPrice()==null) {
				continue;
			}
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeZero(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeOne(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeTwo(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set);
			}
			
		}
		//购买时间
		price = price.multiply(new BigDecimal((String)map.get("order_time")));
		//购买台数
		price = price.multiply(new BigDecimal((String)map.get("order_num")));
		return price;
	}
	
	@Override
	public BigDecimal calculateStandardPrice(Long productId, Long baseRegionId,
			String standardName, String standardValue, Integer orderNum,
			Integer orderTime, String standardType) {
		return calculateStandardPrice(productId, baseRegionId, standardName, standardValue, orderNum, orderTime, standardType, null);
	}

	@Override
	public BigDecimal calculateStandardPrice(Long productId, Long baseRegionId,
			String standardName, String standardValue, Integer orderNum,
			Integer orderTime, String standardType, List<BaseStandard> baseStandards) {
		BigDecimal price = new BigDecimal(0);
		
		if(baseStandards==null) {
			baseStandards = this.baseStandardDao.selectBaseStandardWithPriceByElementName(standardName);
		} 
		
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
				price = getPriceByTypeZero(baseStandard, standardValue, standardType, productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeOne(baseStandard, standardValue, standardType, productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = getPriceByTypeTwo(baseStandard, standardValue, standardType, productPrice, price, set);
			}
			
		}
		
		//购买时间
		price = price.multiply(new BigDecimal(orderTime));
		//购买台数
		price = price.multiply(new BigDecimal(orderNum));
		return price;
	}



}
