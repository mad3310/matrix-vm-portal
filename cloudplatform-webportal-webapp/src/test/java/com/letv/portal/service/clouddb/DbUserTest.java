package com.letv.portal.service.clouddb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.junitBase.AbstractTest;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.service.IDbUserService;

public class DbUserTest extends AbstractTest{
	
	@Resource
	private IDbUserService dbUserService;
	@Resource
	private IDbUserProxy dbUserProxy;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
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
	@Test
	public void InsertDbUser(){
		DbUserModel dbUserModel = new DbUserModel();
		dbUserModel.setAcceptIp("%");
		dbUserModel.setUsername("admin");
		dbUserModel.setPassword("admin");
		dbUserModel.setMaxConcurrency(1000);
	    this.dbUserService.insert(dbUserModel);
	    System.out.println(dbUserModel.getId());
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
	/**
	 * Methods Name: updateDbUser <br>
	 * Description: 修改dbUser信息
	 * @author name: wujun
	 */
	@Test
    public  void updateDbUser(){
		try {
			DbUserModel dbUserModel = new DbUserModel();
			dbUserModel.setId(1L);
			dbUserModel.setUsername("wujunTest1");
			this.dbUserService.update(dbUserModel);
		} catch (Exception e) {
		System.out.print(e.getMessage());
		}

    }
	/**
	 * Methods Name: deleteDbUserById <br>
	 * Description: 删除dbUser信息
	 * @author name: wujun
	 */
	@Test
    public  void deleteDbUserById(){
		DbUserModel dbUserModel = new DbUserModel();
		dbUserModel.setId(1L);
		this.dbUserService.delete(dbUserModel);
    }
	/**
	 * Methods Name: deleteDbUserById <br>
	 * Description: 删除dbUser信息
	 * @author name: wujun
	 */
	/**
	 * Methods Name: test <br>
	 * Description: <br>
	 * @author name: wujun
	 */
	@Test
    public  void testStringArgs(){
		String id = "29,";
		String tg[]=id.split(",");
	    System.out.print(tg.length);
    }
	
	@Test
	public void testUpdateUserAuthority() {
		DbUserModel dbUser = new DbUserModel();
		dbUser.setUsername("sss");
		dbUser.setDbId(21L);
		dbUser.setMaxConcurrency(50);
//		String ips="10.58.166.21,10.58.166.34,10.58.166.35,192.168.30.21";
//		String types="1,2,3,1";
		String ips="10.58.166.21,10.58.166.34,10.58.166.35";
		String types="1,1,1";
		
		dbUser.setCreateUser(2L);
		this.dbUserProxy.updateUserAuthority(dbUser, ips, types);
	}
	 @Test
    public void getStepByDbId() {
    	DbUserModel dbUser = new DbUserModel();
    	dbUser.setUsername("test_jll");
    	dbUser.setDbId(14L);
    	dbUser.setDescn("hello world");
    	this.dbUserService.updateDescnByUsername(dbUser);
    }
	
}
