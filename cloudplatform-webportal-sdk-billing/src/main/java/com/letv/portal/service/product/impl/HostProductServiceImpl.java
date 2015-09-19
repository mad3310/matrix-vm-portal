package com.letv.portal.service.product.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.dao.base.IBaseStandardDao;
import com.letv.portal.dao.product.IProductElementDao;
import com.letv.portal.model.base.BaseStandard;
import com.letv.portal.model.product.ProductElement;
import com.letv.portal.service.product.IHostProductService;

@Service("hostProductService")
public class HostProductServiceImpl extends ProductServiceImpl implements IHostProductService {
	
	private final static Logger logger = LoggerFactory.getLogger(HostProductServiceImpl.class);
	
	@Autowired
	private IProductElementDao productElementDao;
	@Autowired
	private IBaseStandardDao baseStandardDao;
	
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
	public boolean validateData(Long id, Map<String, Object> map) {
		//**********验证产品在该地域是否存在start******************
		if(!super.validateRegion(id, map)) {
			return false;
		}
		//**********验证产品在该地域是否存在end******************
		
		Map<String, Set<String>> standards = new HashMap<String, Set<String>>();
		Map<String, String> chargeTypes = new HashMap<String, String>();
		
		getStandardsInfoByProductElements(id, map, standards, chargeTypes);
		
		for (String standard : standards.keySet()) {
			if(chargeTypes.get(standard)==null) {
				continue;
			}
			if(Integer.parseInt((String)chargeTypes.get(standard))==0) {//基础定价
				if(!super.validateChargeTypeZero(map, standard, standards)) {
					return false;
				}
			} else if(Integer.parseInt((String)chargeTypes.get(standard))==1 || Integer.parseInt((String)chargeTypes.get(standard))==2) {//阶梯定价/线性定价
				if(!super.validateChargeTypeOneOrTwo(map, standard, standards)) {
					return false;
				}
			} else if(Integer.parseInt((String)chargeTypes.get(standard))==3) {
				if(!validateChargeTypeThree(map, standard, standards)) {
					return false;
				}
			}
		}
		
		//**********验证购买产品数量和时长是否在规定范围内start******************
		if(!super.validateOrderNum(map, standards) || !super.validateOrderTime(map)) {
			return false;
		}
		//**********验证购买产品数量和时长是否在规定范围内end******************
		return true;
	}

	protected boolean validateChargeTypeThree(Map<String, Object> map, String standard, Map<String, Set<String>> standards) {
		Iterator<String> it = standards.get(standard).iterator();
		if(map.get(standard)==null || Double.parseDouble((String)map.get(standard))<0 
				|| Double.parseDouble((String)map.get(standard))>Double.parseDouble(it.next())) {
			logger.info("validateData, map "+standard+" standard is :"+map.get(standard)+", standard not within range");
			return false;
		}
		return true;
	}
	
	@Override
	protected void getStandardsInfoByProductElements(Long id,
			Map<String, Object> map, Map<String, Set<String>> standards,
			Map<String, String> chargeTypes) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.clear();
		params.put("productId", id);
		List<ProductElement> productElements = this.productElementDao.selectByMap(params);
		for (ProductElement productElement : productElements) {
			params.clear();
			params.put("baseElementId", productElement.getBaseElementId());
			List<BaseStandard> baseStandards = this.baseStandardDao.selectByMap(params);
			for (BaseStandard baseStandard : baseStandards) {
				Set<String> set = null;
				if(standards.containsKey(baseStandard.getStandard())) {
					set = standards.get(baseStandard.getStandard());
					if(baseStandard.getBasePrice()!=null && (Integer.parseInt(baseStandard.getBasePrice().getType())==1 || Integer.parseInt(baseStandard.getBasePrice().getType())==2
							 || Integer.parseInt(baseStandard.getBasePrice().getType())==3)) {//阶梯定价或线性定价
						if(Double.parseDouble(set.iterator().next())<Double.parseDouble(baseStandard.getValue())) {//只放入最大值
							set.clear();
						}
					}
					set.add(baseStandard.getValue());
				} else {
					set = new HashSet<String>();
					set.add(baseStandard.getValue());
					standards.put(baseStandard.getStandard(), set);
				}
			}
			if(baseStandards!=null && baseStandards.size()!=0 && baseStandards.get(0).getBasePrice()!=null) {
				chargeTypes.put(baseStandards.get(0).getStandard(), baseStandards.get(0).getBasePrice().getType());
			}
		}
	}

}
