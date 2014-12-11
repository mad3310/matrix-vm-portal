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

import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.service.IDbUserService;

/**Program Name: DbUserController <br>
 * Description:  db用户<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月13日 <br>
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
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	private final static Logger logger = LoggerFactory.getLogger(DbUserController.class);
		
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
		List<DbUserModel> dbUsers = this.dbUserService.selectByDbId(dbId); 
		if(dbUsers.size()>0) {
			if(dbUsers.get(0).getCreateUser() == sessionService.getSession().getUserId()) {
				obj.setData(dbUsers);
			} else {
				obj.setResult(0);
			}
		} else {
			obj.setData(dbUsers);
		}
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 保存创建信息  http://localhost:8080/db/user/save<br>
	 * @author name: liuhao1
	 * @param dbApplyStandardModel
	 * @param request
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResultObject save(DbUserModel dbUserModel) {
		dbUserModel.setCreateUser(sessionService.getSession().getUserId());
		this.dbUserProxy.saveAndBuild(dbUserModel);
		ResultObject obj = new ResultObject();
		return obj;
	}
	/**
	 * Methods Name: validate <br>
	 * Description: 校验dbUser用户是否存在
	 * @author name: wujun
	 * @param dbUserModel
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/validate",method=RequestMethod.POST)
	public @ResponseBody Map<String,Object> validate(DbUserModel dbUserModel,HttpServletRequest request) {
		Map<String,Object> map = new HashMap<String,Object>();
		List<DbUserModel> list = this.dbUserService.selectByIpAndUsername(dbUserModel);
		map.put("valid", list.size()>0?false:true);
		return map;
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
	public  @ResponseBody ResultObject deleteDbUserById(@PathVariable String dbUserId,DbUserModel dbUserModel) {
		DbUserModel dbUser = this.dbUserProxy.selectById(Long.parseLong(dbUserId));
		ResultObject obj = new ResultObject();
		if(dbUser!= null) {
			if(dbUser.getCreateUser() == sessionService.getSession().getUserId()) {
				this.dbUserProxy.deleteDbUser(dbUserId);
			} else {
				obj.setResult(0);
			}
		} else {
			obj.setResult(0);
		}
		return obj;
	}
	/**
	 * Methods Name: updateDbUser <br>
	 * Description: 修改DbUser信息
	 * @author name: wujun
	 * @param dbUserModel
	 * @return
	 */
	@RequestMapping(value="/{dbUserId}",method=RequestMethod.POST)
	public @ResponseBody ResultObject updateDbUser(DbUserModel dbUserModel) {
		ResultObject obj = new ResultObject();
		DbUserModel dbUser = this.dbUserProxy.selectById(dbUserModel.getId());
		if(dbUser!= null) {
			if(dbUserModel.getId() == sessionService.getSession().getUserId()) {
				this.dbUserProxy.updateDbUser(dbUserModel);		
			} else {
				obj.setResult(0);
			}
		} else {
			obj.setResult(0);
		}
		return obj;
	}
	
	
}
