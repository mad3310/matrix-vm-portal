package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.model.DbModel;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService;

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
	
	@Resource
	private IDbService dbService;
	@Resource
	private IContainerService containerService;
	@Resource
	private IMclusterService mclusterService;
	@Resource
	private IDbUserService dbUserService;
	
	@Autowired
	private IDbProxy dbProxy;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	private final static Logger logger = LoggerFactory.getLogger(DbController.class);
	
	/**Methods Name: list <br>
	 * Description: http://localhost:8080/db/list/${currentPage}/${recordsPerPage}/${dbName}<br>
	 * @author name: liuhao1
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{dbName}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String dbName) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);		
		params.put("createUser", sessionService.getSession().getUserId());		
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.findPagebyParams(params, page));
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存创建信息  http://localhost:8080/db/<br>
	 * @author name: liuhao1
	 * @param dbApplyStandardModel
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(DbModel dbModel) {
		this.dbProxy.saveAndBuild(dbModel);
		ResultObject obj = new ResultObject();
		return obj;
	}
	
	/**Methods Name: detail <br>
	 * Description: 根据id获取db信息 http://localhost:8080/db/detail/{dbId}<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{dbId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long dbId){
		ResultObject obj = new ResultObject();
		DbModel db = this.dbProxy.dbList(dbId);
		if(db.getCreateUser() == sessionService.getSession().getUserId()) {
			obj.setData(db);
		} else {
			obj.setResult(0);
		}
		return obj;
	}	
	
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validate(String dbName,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<DbModel> list = this.dbService.selectByDbName(dbName);
		map.put("valid", list.size()>0?false:true);
		return map;
	}
	
}
