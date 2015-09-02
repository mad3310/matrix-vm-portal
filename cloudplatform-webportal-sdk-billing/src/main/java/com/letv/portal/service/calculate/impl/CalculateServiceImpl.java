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
import com.letv.portal.dao.product.IProductDao;
import com.letv.portal.dao.product.IProductElementDao;
import com.letv.portal.dao.product.IProductPriceDao;
import com.letv.portal.dao.product.IProductRegionDao;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.product.ProductElement;
import com.letv.portal.model.product.ProductPrice;
import com.letv.portal.service.calculate.ICalculateService;

@Service("calculateService")
public class CalculateServiceImpl implements ICalculateService {
	
	@Autowired
	private IProductDao productDao;
	@Autowired
	private IProductElementDao productElementDao;
	@Autowired
	private IBaseStandardDao baseStandardDao;
	@Autowired
	private IProductRegionDao productRegionDao;
	@Autowired
	private IProductPriceDao productPriceDao;
	
	@Override
	public Double calculatePrice(Long productId, Map<String, Object> map) {
		Double price = 0d;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("productId", productId);
		List<ProductElement> productElements = this.productElementDao.selectByMap(params);
		
		List<BaseStandard> baseStandards = new ArrayList<BaseStandard>();
		for (ProductElement productElement : productElements) {
			List<BaseStandard> stand = this.baseStandardDao.selectBaseStandardWithPrice(productElement.getBaseElementId());
			baseStandards.addAll(stand);
		}
		//记录计算过的standard
		Set<String> set = new HashSet<String>();
		for (BaseStandard baseStandard : baseStandards) {
			if(set.contains(baseStandard.getStandard())) {
				continue;
			}
			params.put("baseRegionId", map.get("region"));
			params.put("basePriceId", baseStandard.getBasePrice().getId());
			params.put("used", 1);
			params.put("deleted", 0);
			params.put("date", new Date());
			ProductPrice productPrice = this.productPriceDao.selectProductPriceByMap(params);
			if("0".equals(baseStandard.getBasePrice().getType())) {
				if(baseStandard.getValue().equals(map.get(baseStandard.getStandard()))) {
					set.add(baseStandard.getStandard());
					if(productPrice!=null && productPrice.getPrice()!=null) {
						price = Arithmetic4Double.add(price, productPrice.getPrice());
					} else {
						price = Arithmetic4Double.add(price, baseStandard.getBasePrice().getPrice());
					}
				}
			} else if("1".equals(baseStandard.getBasePrice().getType())) {
				String[] str = baseStandard.getBasePrice().getAmount().split("-");
				if(Double.parseDouble(str[0])<=Double.parseDouble((String)map.get(baseStandard.getStandard())) && Double.parseDouble(str[1])>=Double.parseDouble((String)map.get(baseStandard.getStandard()))) {
					set.add(baseStandard.getStandard());
					if(productPrice!=null && productPrice.getPrice()!=null) {
						Double ret = Arithmetic4Double.multi(productPrice.getPrice(), Double.parseDouble((String)map.get(baseStandard.getStandard()))/1024/1024/1024);
						price = Arithmetic4Double.add(price, ret);
					} else {
						Double ret = Arithmetic4Double.multi(baseStandard.getBasePrice().getPrice(), Double.parseDouble((String)map.get(baseStandard.getStandard()))/1024/1024/1024);
						price = Arithmetic4Double.add(price, ret);
					}
					
				}
			} else if("2".equals(baseStandard.getBasePrice().getType())) {
				set.add(baseStandard.getStandard());
				if(productPrice!=null && productPrice.getPrice()!=null) {
					Double ret = Arithmetic4Double.multi(productPrice.getPrice(), Double.parseDouble((String)map.get(baseStandard.getStandard()))/1024/1024/1024);
					price = Arithmetic4Double.add(price, ret);
				} else {
					Double ret = Arithmetic4Double.multi(baseStandard.getBasePrice().getPrice(), Double.parseDouble((String)map.get(baseStandard.getStandard()))/1024/1024/1024);
					price = Arithmetic4Double.add(price, ret);
				}
				
			}
			
		}
		//购买时间
		price = Arithmetic4Double.multi(price, Double.parseDouble((String)map.get("order_time")));
		//购买台数
		price = Arithmetic4Double.multi(price, Double.parseDouble((String)map.get("order_num")));
		return price;
	}



}
