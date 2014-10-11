package com.letv.portal.clouddb.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.result.ResultObject;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.service.IDbUserService;

public class DbUserControllerTest extends AbstractTest{
	
	@Resource
	private IDbUserService dbUserService;
	
	/**
	 * Methods Name: list <br>
	 * Description:/{dbId}
	 * @author name: wujun
	 * @param dbId
	 * @param request
	 * @return
	 */
	@Test
	public void list() {
		Long dbId = 1L;
		ResultObject obj = new ResultObject();
		obj.setData(this.dbUserService.selectByDbId(dbId));
	    System.out.println(obj);
	}
	
	/**Methods Name: save <br>
	 * Description: 保存创建信息 
	 * @author name: wujun
	 * @param dbApplyStandardModel
	 * @param request
	 * @return
	 */
	@Test
	public void save() {
			DbUserModel dbUserModel = new DbUserModel();
			String acceptIp = "192.168.1.4,192.168.1.5";
			dbUserModel.setMaxConcurrency(0);
			dbUserModel.setAcceptIp(acceptIp);		
			this.dbUserService.insertDbUserAndAcceptIp(dbUserModel);
	}
	
	/**
	 * Methods Name: validate <br>
	 * Description: /validate/{dbId}/{username}/{acceptIp}
	 * @author name: wujun
	 * @param dbUserModel
	 * @param request
	 * @return
	 */
	@Test
	public void validate() {
		DbUserModel dbUserModel = new DbUserModel();
		dbUserModel.setAcceptIp("192.168.1.2");
		dbUserModel.setUsername("test1");
		dbUserModel.setDbId(1L);
		Map<String,Object> map = new HashMap<String,Object>();
		List<DbUserModel> list = this.dbUserService.selectByIpAndUsername(dbUserModel);
		map.put("valid", list.size()>0?false:true);
		System.out.println(map.get("valid"));
	}
}
