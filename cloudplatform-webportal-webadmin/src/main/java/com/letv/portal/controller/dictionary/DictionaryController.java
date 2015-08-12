package com.letv.portal.controller.dictionary;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.adminoplog.AoLogType;
import com.letv.portal.model.dictionary.Dictionary;
import com.letv.portal.service.adminoplog.AoLog;
import com.letv.portal.service.adminoplog.ClassAoLog;
import com.letv.portal.service.dictionary.IDictionaryService;

@ClassAoLog(module="通用管理/字典管理")
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
	
	@Autowired
	private IDictionaryService dictionaryService;
	
	private final static Logger logger = LoggerFactory.getLogger(DictionaryController.class);

	/**
	  * @Title: list
	  * @Description: 获取指定页数指定条数的Dictionary列表
	  * @param page
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午1:47:31
	  */
	@RequestMapping(method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(Page page,HttpServletRequest request,ResultObject obj) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		obj.setData(this.dictionaryService.selectPageByParams(page, params));
		return obj;
	}
	
	/**
	  * @Title: saveDictionary
	  * @Description: 保存Dictionary信息
	  * @param dictionary
	  * @param request
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午5:48:00
	  */
	@AoLog(desc="创建字典信息",type=AoLogType.INSERT)
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody ResultObject saveDictionary(Dictionary dictionary) {
		ResultObject obj = new ResultObject();
		this.dictionaryService.insert(dictionary);
		return obj;
	}
	
	/**
	  * @Title: deleteDbUserById
	  * @Description: 根据主键id删除字典信息
	  * @param dictionaryId 主键id
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午5:55:50
	  */
	@AoLog(desc="根据主键id删除字典信息",type=AoLogType.DELETE)
	@RequestMapping(value="/{dictionaryId}",method=RequestMethod.DELETE)
	public  @ResponseBody ResultObject deleteDictionaryById(@PathVariable Long dictionaryId) {
		Dictionary dictionary = new Dictionary();
		dictionary.setId(dictionaryId);
		this.dictionaryService.delete(dictionary);
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**
	  * @Title: updateDbUser
	  * @Description: 修改Dictionary信息
	  * @param dictionary
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午5:58:17
	  */
	@AoLog(desc="修改字典信息",type=AoLogType.UPDATE)
	@RequestMapping(value="/{dictionaryId}", method=RequestMethod.POST)
	public @ResponseBody ResultObject updateDictionary(Dictionary dictionary) {
		this.dictionaryService.update(dictionary);		
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**
	  * @Title: detail
	  * @Description: 根据id返回详情
	  * @param id
	  * @param request
	  * @param obj
	  * @return ResultObject   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年6月30日 下午6:47:24
	  */
	@RequestMapping(value ="/{dictionaryId}",method=RequestMethod.GET)   
	public @ResponseBody ResultObject detailById(@PathVariable Long dictionaryId){
		ResultObject obj = new ResultObject();	
		obj.setData(this.dictionaryService.selectById(dictionaryId));
		return obj;
	}
	
}
