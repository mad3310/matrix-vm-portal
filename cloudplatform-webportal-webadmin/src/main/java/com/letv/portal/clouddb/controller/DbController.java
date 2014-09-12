package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbApplyStandardService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IHostService;
import com.letv.portal.service.IMclusterService;
import com.letv.portal.service.IPythonService;
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
	@Resource
	private IHostService hostService;
	@Resource
	private IPythonService pythonService;
	
	private final static Logger logger = LoggerFactory.getLogger(DbController.class);
	
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public String toList(HttpServletRequest request){
		return "/clouddb/mgr_db_list";
	}
	
	/**Methods Name: list <br>
	 * Description: http://localhost:8080/db/list/<br>
	 * @author name: liuhao1
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 */
	@RequestMapping(value="/list/{currentPage}/{recordsPerPage}/{dbName}",method=RequestMethod.GET)  
	public @ResponseBody ResultObject list(@PathVariable int currentPage,@PathVariable int recordsPerPage,@PathVariable String dbName,HttpServletRequest request) {
		Page page = new Page();
		page.setCurrentPage(currentPage);
		page.setRecordsPerPage(recordsPerPage);
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);
		
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.findPagebyParams(params, page));
		return obj;
	}
	
	/**Methods Name: detail <br>
	 * Description: 查看详情<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/detail/{dbId}",method=RequestMethod.GET)
	public ModelAndView detail(@PathVariable String dbId,HttpServletRequest request){
		DbModel dbModel = this.dbService.selectById(dbId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("containers", this.containerService.selectByClusterId(dbModel.getClusterId()));
		mav.addObject("dbUsers", this.dbUserService.selectByDbId(dbId));
		mav.addObject("dbApplyStandard", this.dbApplyStandardService.selectByDbId(dbId));
		mav.addObject("db", dbModel);
		mav.setViewName("/clouddb/mgr_db_apply_info");
		return mav;
	}
	
	/**Methods Name: audit <br>
	 * Description: 审批页面<br>
	 * @author name: liuhao1
	 * @param dbId
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/audit/{dbId}",method=RequestMethod.GET)
	public ModelAndView audit(@PathVariable String dbId,HttpServletRequest request){
		ModelAndView mav = new ModelAndView();
		mav.addObject("hosts", this.hostService.selectByMap(null));
		mav.addObject("mclusters", this.mclusterService.selectByMap(null));
		mav.addObject("dbApplyStandard", this.dbApplyStandardService.selectByDbId(dbId));
		
		mav.setViewName("/clouddb/mgr_audit_db");
		return mav;
	}

	/**Methods Name: save <br>
	 * Description: 保存审批信息 //http://localhost:8080/api/db/audit/save<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/audit/save",method=RequestMethod.POST)   
	public String save(DbAuditView dav,HttpServletRequest request) {
		String auditType = dav.getAuditType();
		String auditInfo = dav.getAuditInfo();
		String mclusterId = dav.getMclusterId();
		String dbId = dav.getDbId();
		String dbName = dav.getApplyCode();
		String dbApplyStandardId = dav.getDbApplyStandardId();
		String hostIds = dav.getHostIds();
		String createUser = dav.getCreateUser();
		
		if(Constant.DB_AUDIT_STATUS_TRUE_BUILD_NEW_MCLUSTER.equals(auditType)) {
			mclusterId = UUID.randomUUID().toString();
			this.mclusterService.insert(mclusterId,hostIds.split(","),dbName,createUser);
		}
		//保存审批信息
		this.dbService.audit(dbId,dbApplyStandardId,auditType,mclusterId,auditInfo);
		
		//创建mcluster db
//		this.dbService.build(auditType,mclusterId,dbId,dbApplyStandardId);
		
		return "redirect:/db/list";
		
	}
	
	@RequestMapping("/build")   //http://localhost:8080/api/db/build
	public @ResponseBody ResultObject build(DbModel dbModel,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.pythonService.initContainer();
		return obj;
	}
	
	/*@RequestMapping("/build/notice/{buildFlag}/{dbId}")   //http://localhost:8080/api/db/build/notice/{success/fail}/{dbId}
	public ResultObject notice(@PathVariable String buildFlag,@PathVariable String dbId,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		this.dbService.buildNotice(dbId,buildFlag);
		return obj;
	}*/
}
