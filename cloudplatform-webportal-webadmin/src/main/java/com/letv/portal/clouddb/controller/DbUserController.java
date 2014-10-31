package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IDbUserService;

/**Program Name: DbUserController <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/dbUser")
public class DbUserController {
	
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IDbUserProxy dbUserProxy;
	@Resource
	private IBuildTaskService buildTaskService;
	
	private final static Logger logger = LoggerFactory.getLogger(DbUserController.class);
	

	/**Methods Name: list <br>
	 * Description: 根据dbName查询相关dbUser分页数据<br>
	 * @author name: liuhao1
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{dbName}",method=RequestMethod.GET)  
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String dbName,HttpServletRequest request) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);
		
		ResultObject obj = new ResultObject();
		obj.setData(this.dbUserService.findPagebyParams(params, page));
		return obj;
	} 
	/**Methods Name: list <br>
	 * Description: db列表 http://localhost:8080/db/user/list/{dbId}<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{dbId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable Long dbId) {
		ResultObject obj = new ResultObject();
		obj.setData(this.dbUserService.selectByDbId(dbId));
		return obj;
	}
	/**
	 * Methods Name: list <br>
	 * Description: 审批DbUser
	 * @author name: wujun
	 * @param ids
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)  
	public @ResponseBody ResultObject auditDbUser(String dbUserId,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.dbUserProxy.buildDbUser(dbUserId);
		return obj;
	}
	
	/**
	 * Methods Name: deleteDbUserById <br>
	 * Description: 删除dbUser用户
	 * @author name: wujun
	 * @param dbUserId
	 * @param dbUserModel
	 * @return
	 */
	@RequestMapping(value="/{dbUserId}",method=RequestMethod.DELETE)
	public  @ResponseBody ResultObject deleteDbUserById(@PathVariable String dbUserId) {
		this.dbUserService.deleteDbUser(dbUserId);
		ResultObject obj = new ResultObject();
		return obj;
	}
	/**
	 * Methods Name: updateDbUser <br>
	 * Description: 修改DbUser信息
	 * @author name: wujun
	 * @param dbUserModel
	 * @return
	 */
	@RequestMapping(value="/{dbUserId}", method=RequestMethod.POST)
	public @ResponseBody ResultObject updateDbUser(DbUserModel dbUserModel) {
		this.dbUserService.updateDbUser(dbUserModel);		
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	
}
