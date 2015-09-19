package com.letv.portal.service.product.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.letv.portal.service.product.IHostProductService;

@Service("hostProductService")
public class HostProductServiceImpl extends ProductServiceImpl implements IHostProductService {
	
	private final static Logger logger = LoggerFactory.getLogger(HostProductServiceImpl.class);
	
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
		
		super.getStandardsInfoByProductElements(id, map, standards, chargeTypes);
		
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

}
