package com.letv.portal.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.letv.portal.dao.base.IBaseStandardDao;
import com.letv.portal.dao.product.IProductElementDao;
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
		
		Map<String, List<Map<String, String>>> elements = new HashMap<String, List<Map<String, String>>>();
		Map<String, String> chargeTypes = new HashMap<String, String>();
		
		super.getStandardsInfoByProductElements(id, map, elements, chargeTypes);
		
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
	
}
