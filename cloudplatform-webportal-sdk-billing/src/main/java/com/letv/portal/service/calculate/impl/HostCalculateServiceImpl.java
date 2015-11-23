package com.letv.portal.service.calculate.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.dao.base.IBaseStandardDao;
import com.letv.portal.dao.product.IProductPriceDao;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.product.ProductPrice;
import com.letv.portal.service.calculate.IHostCalculateService;
/**
 * 云主机计费
 * @author lisuxiao
 *
 */
@Service("hostCalculateService")
public class HostCalculateServiceImpl extends CalculateServiceImpl implements IHostCalculateService {
	
	@Autowired
	private IBaseStandardDao baseStandardDao;
	@Autowired
	private IProductPriceDao productPriceDao;
	
	/**
	  * @Title: getPriceByTypeThree
	  * @Description: 计算计费类型为3-云主机双线性费用
	  * @param baseStandard
	  * @param standardValue
	  * @param productPrice
	  * @param p
	  * @param set
	  * @return double   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月18日 下午5:24:23
	  */
	protected BigDecimal getPriceByTypeThree(BaseStandard baseStandard, String standardValue, String standardType,
			ProductPrice productPrice, BigDecimal p, Set<String> set, String across) {
		BigDecimal price = p;
		BigDecimal bdAcross = new BigDecimal(across);
		BigDecimal bdStandardValue = new BigDecimal(standardValue);
		if(standardType==null ? true : baseStandard.equals(standardType)) {
			if(bdStandardValue.compareTo(bdAcross)>0) {
				if(baseStandard.getValue().equals(across)) {
					if(productPrice!=null && productPrice.getPrice()!=null) {
						price = productPrice.getPrice().multiply(bdAcross).add(price);
					} else {
						price = baseStandard.getBasePrice().getPrice().multiply(bdAcross).add(price);
					}
				} else {
					BigDecimal value = bdStandardValue.subtract(bdAcross);
					if(productPrice!=null && productPrice.getPrice()!=null) {
						price = productPrice.getPrice().multiply(value).add(price);
					} else {
						price = baseStandard.getBasePrice().getPrice().multiply(value).add(price);
					}
				}
			} else {
				set.add(baseStandard.getBaseElement().getName());
				if(productPrice!=null && productPrice.getPrice()!=null) {
					price = productPrice.getPrice().multiply(bdStandardValue).add(price);
				} else {
					price = baseStandard.getBasePrice().getPrice().multiply(bdStandardValue).add(price);
				}
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
			baseStandards = super.getBaseStandard(productId);
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		//记录计算过的standard
		Set<String> set = new HashSet<String>();
		params.put("productId", productId);
		params.put("baseRegionId", map.get("region"));
		params.put("used", 1);
		params.put("deleted", 0);
		params.put("date", new Date());
		String across = null;//交界值
		for (BaseStandard baseStandard : baseStandards) {
			if(baseStandard.getBasePrice()!=null && "3".equals(baseStandard.getBasePrice().getType())) {//3-云主机双线性
				if(across==null) {
					across = baseStandard.getValue();
				} else {
					if(Double.parseDouble(across)>Double.parseDouble(baseStandard.getValue())) {
						across = baseStandard.getValue();
					}
				}
			}
		}
		for (BaseStandard baseStandard : baseStandards) {
			//当该元素已经计算或该元素不计费
			if(set.contains(baseStandard.getBaseElement().getName())  
					|| baseStandard.getBasePrice()==null 
					|| baseStandard.getBasePrice().getPrice()==null) {
				continue;
			}
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeZero(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeOne(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeTwo(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set);
			} else if("3".equals(baseStandard.getBasePrice().getType())) {//3-云主机双线性
				price = getPriceByTypeThree(baseStandard, (String)map.get(baseStandard.getBaseElement().getName()), (String)map.get(baseStandard.getBaseElement().getName()+"_type"), productPrice, price, set, across);
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
		String across = null;//交界值
		for (BaseStandard baseStandard : baseStandards) {
			if("3".equals(baseStandard.getBasePrice().getType())) {//3-云主机双线性
				if(across==null) {
					across = baseStandard.getValue();
				} else {
					if(Double.parseDouble(across)>Double.parseDouble(baseStandard.getValue())) {
						across = baseStandard.getValue();
					}
				}
			}
		}
		for (BaseStandard baseStandard : baseStandards) {
			if(set.contains(baseStandard.getBaseElement().getName())) {
				break;
			}
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeZero(baseStandard, standardValue, standardType, productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeOne(baseStandard, standardValue, standardType, productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeTwo(baseStandard, standardValue, standardType, productPrice, price, set);
			} else if("3".equals(baseStandard.getBasePrice().getType())) {//3-云主机双线性
				price = getPriceByTypeThree(baseStandard, standardValue, standardType, productPrice, price, set, across);
			}
			
		}
		
		//购买时间
		price = price.multiply(new BigDecimal(orderTime));
		//购买台数
		price = price.multiply(new BigDecimal(orderNum));
		return price;
	}



}
