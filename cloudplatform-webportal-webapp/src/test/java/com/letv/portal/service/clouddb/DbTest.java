package com.letv.portal.service.clouddb;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.letv.common.paging.impl.Page;
import com.letv.common.result.ResultObject;
import com.letv.portal.constant.Constant;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.DbModel;
import com.letv.portal.service.IContainerService;
import com.letv.portal.service.IDbService;
import com.letv.portal.service.IDbUserService;
import com.letv.portal.service.IMclusterService; 
import com.letv.portal.service.IUserService;

public class DbTest extends AbstractTest{
	@Resource
	private IDbService dbService;
	@Resource
	private IContainerService containerService;
	@Resource
	private IMclusterService mclusterService;
	@Resource
	private IDbUserService dbUserService;
	
	@Resource
	private IUserService  userService;
    /**
     * Methods Name:/db/{currentPage}/{recordsPerPage}/{dbName}
     * Description: <br>
     * @author name: wujun
     */
    
    @Test
	public void list(){
		Page page = new Page();
		page.setCurrentPage(1);
		page.setRecordsPerPage(10);
	
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbName", null);
		params.put("createUser", "11");
		
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.findPagebyParams(params, page));
		System.out.println(obj);
	}
    /**
     * Methods Name: detail <br>
     * Description: /db/detail/{dbId}
     * @author name: wujun
     */
    @Test
    public void detail(){
    	Long dbId = 1L;
		ResultObject obj = new ResultObject();
		obj.setData(this.dbService.selectById(dbId));
		System.out.println(obj);
    }
    /**
     * Methods Name: save <br>
     * Description: /db
     * @author name: wujun
     */
    @Test 
	public void save() {
    	DbModel dbModel = new DbModel();
    	dbModel.setDbName("test12");
    	dbModel.setLinkType(0);
    	dbModel.setEngineType(1);
    	dbModel.setCreateUser(1L);
    	
    	
    	dbModel.setStatus(Constant.STATUS_DEFAULT);
		this.dbService.insert(dbModel);
	}
    /**
     * Methods Name: validate <br>
     * Description: /validate/{dbName}
     * @author name: wujun
     */
    @Test
	public void validate() {
		Map<String,Object> map = new HashMap<String,Object>();
		String dbName ="test";
		List<DbModel> list = this.dbService.selectByDbNameForValidate(dbName,4L);
		map.put("valid", list.size()>0?false:true);
		System.out.println(map.get("valid"));
	}
    @Test
	public void  getUserById() {
		Long userId =1L;
		this.userService.getUserById(userId);
        System.out.println(this.userService.getUserById(userId));
	}
	
}
