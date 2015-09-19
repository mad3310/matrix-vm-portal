package com.letv.portal.service.calculate.impl;

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
	  * @Description: TODO
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
	protected double getPriceByTypeThree(BaseStandard baseStandard, String standardValue, 
			ProductPrice productPrice, double p, Set<String> set, String across) {
		double price = p;
		if(Double.parseDouble(standardValue)>Double.parseDouble(across)) {
			if(baseStandard.getValue().equals(across)) {
				if(productPrice!=null && productPrice.getPrice()!=null) {
					Double ret = Arithmetic4Double.multi(productPrice.getPrice(), Double.parseDouble(across));
					price = Arithmetic4Double.add(price, ret);
				} else {
					Double ret = Arithmetic4Double.multi(baseStandard.getBasePrice().getPrice(), Double.parseDouble(across));
					price = Arithmetic4Double.add(price, ret);
				}
			} else {
				double value = Arithmetic4Double.sub(Double.parseDouble(standardValue), Double.parseDouble(across));
				if(productPrice!=null && productPrice.getPrice()!=null) {
					Double ret = Arithmetic4Double.multi(productPrice.getPrice(), value);
					price = Arithmetic4Double.add(price, ret);
				} else {
					Double ret = Arithmetic4Double.multi(baseStandard.getBasePrice().getPrice(), value);
					price = Arithmetic4Double.add(price, ret);
				}
			}
		} else {
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
	
	@Override
	public Double calculatePrice(Long productId, Map<String, Object> map) {
		Double price = 0d;
		
		List<BaseStandard> baseStandards = super.getBaseStandard(productId);
		
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
			if(set.contains(baseStandard.getStandard())) {
				continue;
			}
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeZero(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeOne(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeTwo(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set);
			} else if("3".equals(baseStandard.getBasePrice().getType())) {//3-云主机双线性
				price = getPriceByTypeThree(baseStandard, (String)map.get(baseStandard.getStandard()), productPrice, price, set, across);
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
				price = super.getPriceByTypeZero(baseStandard, standardValue, productPrice, price, set);
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeOne(baseStandard, standardValue, productPrice, price, set);
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				price = super.getPriceByTypeTwo(baseStandard, standardValue, productPrice, price, set);
			}
			
		}
		
		//购买时间
		price = Arithmetic4Double.multi(price, orderTime);
		//购买台数
		price = Arithmetic4Double.multi(price, orderNum);
		return price;
	}



}
