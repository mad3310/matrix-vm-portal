
package com.letv.portal.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.common.util.ConfigUtil;
import com.letv.common.util.HttpUtil;
import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IDbService;
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
	
	private final static Logger logger = LoggerFactory.getLogger(DbAPI.class);
	
	/**Methods Name: list <br>
	 * Description: 根据查询条件及分页信息获取分页数据<br>
	 * @author name: liuhao1
	 * @param request
	 * @return
	 */
	@RequestMapping("/list")   //http://localhost:8080/api/db/list?currentPage=1&recordsPage=2&dbName=
	public ResultObject list(HttpServletRequest request) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
		Page page = new Page();
		String currentPage = (String) params.get("currentPage");
		String recordsPerPage = (String) params.get("recordsPerPage");
		page.setCurrentPage(StringUtils.isNullOrEmpty(currentPage)?1:Integer.parseInt(currentPage));
		page.setRecordsPerPage(StringUtils.isNullOrEmpty(recordsPerPage)?10:Integer.parseInt(recordsPerPage));
	
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.findPagebyParams(params, page));
		return obj;
	}
	
	@RequestMapping("/audit/list")   //http://localhost:8080/api/db/audit/list?currentPage=1&recordsPage=2&dbName=
	public ResultObject auditList(HttpServletRequest request) {
		Map<String,Object> params = HttpUtil.requestParam2Map(request);
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
	
	@RequestMapping("/save")   //http://localhost:8080/api/db/save
	public ResultObject save(DbModel dbModel, HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		
		/*mclusterModel.setCreateUser(createUser);
		mclusterModel.setCreateTime(createTime);
		mclusterModel.setUpdateUser(updateUser);
		mclusterModel.setUpdateTime(updateTime);*/
		
		if(StringUtils.isNullOrEmpty(dbModel.getId())) {
			this.dbService.insert(dbModel);
		} else {
			this.dbService.updateBySelective(dbModel);
		}
		return obj;
	}
	
	
	@RequestMapping("/build")   //http://localhost:8080/api/db/build
	public ResultObject build(DbModel dbModel,HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		
		/*mclusterModel.setCreateUser(createUser);
		mclusterModel.setCreateTime(createTime);
		mclusterModel.setUpdateUser(updateUser);
		mclusterModel.setUpdateTime(updateTime);*/
		dbModel.setClusterId("0e7e5fba-274f-11e4-a3d9-b82a72b53876");
		
		logger.debug("python.createContainer.url==>"+ConfigUtil.getString("python.createContainer.url"));
		
//		this.dbService.build(dbModel);
		return obj;
	}
	
	@RequestMapping("/audit/save")   //http://localhost:8080/api/db/audit/save
	public ResultObject save(HttpServletRequest request) {
		ResultObject obj = new ResultObject();
		
		String clusterId = request.getParameter("clusterId");
		String dbId = request.getParameter("dbId");
		String dbApplyStandardId = request.getParameter("dbApplyStandardId");
		
		logger.debug("clusterId========>" + clusterId);
		logger.debug("dbId========>" + dbId);
		logger.debug("dbApplyStandardId========>" + dbApplyStandardId);
		
		String[] containerNames = request.getParameter("containerName").split("\\|");
		String[] mountDirs = request.getParameter("mountDir").split("\\|");
		String[] zookeeperIds = request.getParameter("zookeeperId").split("\\|");
		String[] ipAddrs = request.getParameter("ipAddr").split("\\|");
		String[] gateAddrs = request.getParameter("gateAddr").split("\\|");
		String[] ipMasks = request.getParameter("ipMask").split("\\|");
		String[] clusterNodeNames = request.getParameter("clusterNodeName").split("\\|");
		String[] assignNames = request.getParameter("assignName").split("\\|");
		String[] originNames = request.getParameter("originName").split("\\|");
		String[] types = request.getParameter("type").split("\\|");
		
		String createUser = request.getParameter("createUser");
			
		List<ContainerModel> containers = new ArrayList<ContainerModel>();
		for (int i = 0; i < containerNames.length; i++) {
			ContainerModel t = new ContainerModel();
			t.setContainerName(containerNames[i]);
			t.setMountDir(mountDirs[i]);
			t.setZookeeperId(zookeeperIds[i]);
			t.setIpAddr(ipAddrs[i]);
			t.setGateAddr(gateAddrs[i]);
			t.setIpMask(ipMasks[i]);
			t.setClusterNodeName(clusterNodeNames[i]);
			t.setAssignName(assignNames[i]);
			t.setOriginName(originNames[i]);
			t.setType(types[i]);
			
			t.setClusterId(clusterId);
			t.setCreateUser(createUser);
			
			containers.add(t);
			logger.debug("container====>" + t);
		}
		this.dbService.audit(dbId,dbApplyStandardId,containers);
		
		return obj;
	}
	
	
}
