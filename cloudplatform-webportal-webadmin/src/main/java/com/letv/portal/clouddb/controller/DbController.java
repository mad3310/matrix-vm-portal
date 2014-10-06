package com.letv.portal.clouddb.controller;

import java.util.HashMap;
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
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.result.ResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.DbModel;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.proxy.IMclusterProxy;

/**Program Name: DbController <br>
 * Description:  db数据库的相关操作<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月9日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/db")
public class DbController {
	
	@Autowired
	private IDbProxy dbProxy;
	@Autowired
	private IMclusterProxy mclusterProxy;
	
	private final static Logger logger = LoggerFactory.getLogger(DbController.class);
	
	
	/**Methods Name: toList <br>
	 * Description: 页面跳转<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String toList(HttpServletRequest request){
		return "/clouddb/db_list";
	}
	
	/**Methods Name: list <br>
	 * Description: 查询db列表<br>
	 * @author name: liuhao1
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{dbName}",method=RequestMethod.GET)  
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String dbName,ResultObject result) {
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);
		
		result.setData(this.dbProxy.selectPageByParams(currentPage,recordsPerPage,params));
		return result;
	}
	
	/**Methods Name: detail <br>
	 * Description: 查看详情<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{dbId}",method=RequestMethod.GET)
	public ModelAndView detail(@PathVariable Long dbId){
		DbModel dbModel = this.dbProxy.selectById(dbId);
		ModelAndView mav = new ModelAndView();
//		mav.addObject("containers", this.containerService.selectByClusterId(dbModel.getClusterId()));
//		mav.addObject("dbUsers", this.dbUserService.selectByDbId(dbId));
//		mav.addObject("dbApplyStandard", this.dbApplyStandardService.selectByDbId(dbId));
		mav.addObject("db", dbModel);
		mav.setViewName("/clouddb/db_detail");
		return mav;
	}
	
	/**
	 * Methods Name: aduitOrDetail <br>
	 * Description: <br>
	 * @author name: wujun
	 * @param status 1就是查看详情，2就是审批页面
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{status}/{dbId}",method=RequestMethod.GET)
	public ModelAndView aduitOrDetail(@PathVariable Integer status,@PathVariable Long dbId,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		if(null!=status&&!" ".equals(status)){
			if(1 == status){
				DbModel dbModel = this.dbProxy.selectById(dbId);
				mav.addObject("db", dbModel);
				mav.setViewName("/clouddb/mgr_db_detail");
			} else if(2 == status){
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("status", Constant.STATUS_OK);
				mav.addObject("mclusters", this.mclusterProxy.selectByMap(map));
				mav.setViewName("/clouddb/mgr_audit_db");
			}
		}
		return mav;
	}
	
	/**Methods Name: save <br>
	 * Description:<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public void save(Map<String,Object> params) {
		this.dbProxy.auditAndBuild(params);
	}
}
