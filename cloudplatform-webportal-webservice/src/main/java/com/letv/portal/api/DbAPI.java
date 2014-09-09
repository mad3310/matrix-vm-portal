
package com.letv.portal.api;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.HttpUtil;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbApplyStandardModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbApplyStandardService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.view.DbInfoView;
import com.mysql.jdbc.StringUtils;

/**Program Name: DbAPI <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月20日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
@Controller
@RequestMapping("/db")
public class DbAPI {
	@Resource
	private IDbService dbService;
	@Resource
	private IContainerService containerService;
	@Resource
	private IMclusterService mclusterService;
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IDbApplyStandardService dbApplyStandardService;
	
	private final static Logger logger = LoggerFactory.getLogger(DbAPI.class);
	
	/**Methods Name: list <br>
	 * Description: 根据查询条件及分页信息获取分页数据<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/list", method=RequestMethod.POST)   //http://localhost:8080/api/db/list
	@ResponseBody
	public ResultObject list(@RequestBody Map<String,Object> params,HttpServletRequest request) {
		Page page = new Page();
		String currentPage = (String) params.get("currentPage");
		String recordsPerPage = (String) params.get("recordsPerPage");
		page.setCurrentPage(StringUtils.isNullOrEmpty(currentPage)?1:Integer.parseInt(currentPage));
		page.setRecordsPerPage(StringUtils.isNullOrEmpty(recordsPerPage)?10:Integer.parseInt(recordsPerPage));
	
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.findPagebyParams(params, page));
		return obj;
	}
	
	/**Methods Name: auditList <br>
	 * Description: 根据查询条件及分页信息获取分页数据:未审核数据<br>
	 * @author name: liuhao1
	 * @param params
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/audit/list", method=RequestMethod.POST)   //http://localhost:8080/api/db/audit/list
	@ResponseBody
	public ResultObject auditList(@RequestBody Map<String,Object> params,HttpServletRequest request) {
		params.put("status", "0");//未审核
		Page page = new Page();
		String currentPage = (String) params.get("currentPage");
		String recordsPerPage = (String) params.get("recordsPerPage");
		page.setCurrentPage(StringUtils.isNullOrEmpty(currentPage)?1:Integer.parseInt(currentPage));
		page.setRecordsPerPage(StringUtils.isNullOrEmpty(recordsPerPage)?10:Integer.parseInt(recordsPerPage));
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.findPagebyParams(params, page));
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存db<br>
	 * @author name: liuhao1
	 * @param dbModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)   //http://localhost:8080/api/db/save
	@ResponseBody
	public ResultObject save(@RequestBody DbModel dbModel, HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		
		if(StringUtils.isNullOrEmpty(dbModel.getId())) {
			this.dbService.insert(dbModel);
		} else {
			this.dbService.updateBySelective(dbModel);
		}
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存审批信息<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/audit/save",method=RequestMethod.POST)   //http://localhost:8080/api/db/audit/save
	@ResponseBody
	public ResultObject save(@RequestBody Map<String,Object> params,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
//		String auditType = request.getParameter("auditType");
//		String auditInfo = request.getParameter("auditInfo");
//		
//		String mclusterId = request.getParameter("mclusterId");
//		
//		String dbId = request.getParameter("dbId");
//		String dbName = request.getParameter("applyCode");
//		String dbApplyStandardId = request.getParameter("dbApplyStandardId");
//		
//		String hostIds = request.getParameter("hostIds");
//		String createUser = request.getParameter("createUser");
		
		String auditType = (String) params.get("auditType");
		String auditInfo = (String) params.get("auditInfo");
		String mclusterId = (String) params.get("mclusterId");
		String dbId = (String) params.get("dbId");
		String dbName = (String) params.get("applyCode");
		String dbApplyStandardId = (String) params.get("dbApplyStandardId");
		String hostIds = (String) params.get("hostIds");
		String createUser = (String) params.get("createUser");
		
		logger.debug("dbIds==>" + dbId);
		logger.debug("dbName==>" + dbName);
		logger.debug("mclusterId==>" + mclusterId);
		logger.debug("auditType==>" + auditType);
		logger.debug("auditInfo==>" + auditInfo);
		
		if(Constant.DB_AUDIT_STATUS_TRUE_BUILD_NEW_MCLUSTER.equals(auditType)) {
			logger.debug("hostIds==>" + hostIds.toString());
			//审核通过，已有mcluster创建db
			mclusterId = UUID.randomUUID().toString();
			this.mclusterService.insert(mclusterId,hostIds.split("\\|"),dbName,createUser);
		}
		//保存审批信息
		this.dbService.audit(dbId,dbApplyStandardId,auditType,mclusterId,auditInfo);
		
		//创建mcluster db
//		this.dbService.build(auditType,mclusterId,dbId,dbApplyStandardId);
		
		return obj;
	}
	
	/*@RequestMapping("/build")   //http://localhost:8080/api/db/build
	public ResultObject build(DbModel dbModel,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		
		this.dbService.build("0", null, null, null,"liuhao1@letv.com");
		
		return obj;
	}*/
	
	/*@RequestMapping("/build/notice/{buildFlag}/{dbId}")   //http://localhost:8080/api/db/build/notice/{success/fail}/{dbId}
	public ResultObject notice(@PathVariable String buildFlag,@PathVariable String dbId,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.dbService.buildNotice(dbId,buildFlag);
		return obj;
	}*/
	
	/**Methods Name: getInfoById <br>
	 * Description: 根据id获取db信息<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getById/{dbId}",method=RequestMethod.GET) //http://localhost:8080/api/db/getById/{dbId}
	@ResponseBody
	public ResultObject getInfoById(@PathVariable String dbId,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		
		DbModel dbModel = this.dbService.selectById(dbId);
		List<ContainerModel> containers = this.containerService.selectByClusterId(dbModel.getClusterId());
		List<DbUserModel> dbUsers = this.dbUserService.selectByDbId(dbId);
		DbApplyStandardModel dbApplyStandard = this.dbApplyStandardService.selectByDbId(dbId);
		
		DbInfoView dbInfoView = new DbInfoView(dbModel,dbApplyStandard,dbUsers,containers);
		
		obj.setData(dbInfoView);
		return obj;
	}
	
}
