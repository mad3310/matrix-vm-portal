package com.letv.portal.service.operate;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.operate.RecentOperate;
import com.letv.portal.service.IBaseService;


public interface IRecentOperateService extends IBaseService<RecentOperate>  {
	/**
	  * @Title: saveInfo
	  * @Description: 保存操作记录
	  * @param action 动作
	  * @param content 对象名称
	  * @param userId
	  * @param descn void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月10日 下午3:44:44
	  */
	void saveInfo(String action, String content, Long userId, String descn);
	void saveInfo(String action, String content);
	/**
	  * @Title: selectRecentOperate
	  * @Description: 获取最新一个月内记录
	  * @param params
	  * @return List<RecentOperate>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年10月10日 下午3:45:15
	  */
	List<RecentOperate> selectRecentOperate(Map<String, Object> params);
}
