package com.letv.portal.service.pay;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

public interface IPayService {
	/**
	  * @Title: pay
	  * @Description: 去支付
	  * @param paramLong
	  * @param paramMap
	  * @return Map<String,Object>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午4:39:01
	  */
	Map<String, Object> pay(Long paramLong, Map<String, Object> paramMap, HttpServletResponse response);

	/**
	  * @Title: callback
	  * @Description: 支付后回调
	  * @param paramMap
	  * @return boolean   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午4:38:50
	  */
	boolean callback(Map<String, Object> paramMap);

	/**
	  * @Title: queryState
	  * @Description: 查询支付结果
	  * @param paramLong
	  * @return Map<String,Object>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年9月17日 下午4:38:38
	  */
	Map<String, Object> queryState(Long orderId);
}