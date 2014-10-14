package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IDbUserService;

public class DbUserControllerTest extends AbstractTest{
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IBuildTaskService buildTaskService;
	
	private final static Logger logger = LoggerFactory.getLogger(DbUserController.class);
	

	/**Methods Name: list <br>
	 * Description: 根据dbName查询相关dbUser分页数据 /{currentPage}/{recordsPerPage}/{dbName}<br>
	 * @author name: wujun
	 * @param currentPage
	 * @param recordsPerPage
	 * @param dbName
	 * @param request
	 * @return
	 */
	@Test
	public void list() {		
		Page page = new Page();
		page.setCurrentPage(0);
		page.setRecordsPerPage(10);
      	String dbName="wujun";
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", dbName);
		System.out.println(this.dbUserService.findPagebyParams(params, page));
	} 
	/**
	 * Methods Name: list <br>
	 * Description: 审批DbUser /audit
	 * @author name: wujun
	 * @param ids
	 * @param request
	 * @return
	 */
	@Test
	public void audit() {
		String dbUserId = "1";
		this.buildTaskService.buildUser(dbUserId);
	}
}
