package com.letv.portal.clouddb.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.proxy.IDbProxy;
import com.letv.portal.proxy.IMclusterProxy;
import com.letv.portal.view.DbAuditView;

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

	
	/**Methods Name: list <br>
	 * Description: 查询db列表<br>
	 * @author name: liuhao1
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 */
	@RequestMapping(value="/{currentPage}/{recordsPerPage}/{dbName}",method=RequestMethod.GET)  
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String dbName,ResultObject obj) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);		
		obj.setData(this.dbProxy.selectPageByParams(currentPage,recordsPerPage,params));
		return obj;
	}
	
	/**Methods Name: detail <br>
	 * Description: 查看Db详情<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/{dbId}",method=RequestMethod.GET)
	public @ResponseBody ResultObject detail(@PathVariable Long dbId){
		ResultObject obj = new ResultObject();	
		obj.setData(this.dbProxy.dbList(dbId));
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description:审批db
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/audit",method=RequestMethod.POST)   
	public @ResponseBody ResultObject save(DbAuditView dbAuditView) {
		ResultObject obj = new ResultObject();
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbId", dbAuditView.getDbId());
		params.put("mclusterId", dbAuditView.getMclusterId());
		params.put("auditInfo", dbAuditView.getAuditInfo());
		params.put("status", dbAuditView.getAuditType());
		params.put("mclusterName", dbAuditView.getMclusterName());
		
		this.dbProxy.auditAndBuild(params);		
		return obj;
		
	}
	
	
	@RequestMapping(value="/xx",method=RequestMethod.POST)   
	public @ResponseBody ResultObject testXx() {
		ResultObject obj = new ResultObject();
//		this.dbProxy.auditAndBuild(params);		
		return obj;
		
	}
	
	
}
