package com.letv.portal.clouddb.controller;

import java.util.ArrayList;
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

import com.letv.common.exception.ValidateException;
import com.letv.common.result.ResultObject;
import com.letv.common.session.SessionServiceImpl;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.service.IDbUserService;
import com.mysql.jdbc.StringUtils;

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
	 * Description: dbUser列表<br>
	 * @author name: liuhao1 20141225
	 * @param dbId
	 * @return
	 */
	@RequestMapping(value="/{dbId}", method=RequestMethod.GET)   
	public @ResponseBody ResultObject list(@PathVariable Long dbId,ResultObject obj) {
		if(null == dbId) {
			throw new ValidateException("参数不能为空");
		}
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("dbId", dbId);
		params.put("deleted", false);
		List<DbUserModel> dbUsers = this.dbUserService.selectGroupByName(params);
		obj.setData(dbUsers);
		return obj;
	}
	
	/**Methods Name: save <br>
	 * Description: 用户保存<br>
	 * @author name: liuhao1 20141226
	 * @param dbUserModel
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody ResultObject save(DbUserModel dbUserModel,String types,String ips,ResultObject obj) {
		if(StringUtils.isNullOrEmpty(types) || StringUtils.isNullOrEmpty(ips)) {
			throw new ValidateException("参数不能为空");
		}
		this.dbUserProxy.saveAndBuild(dbUserModel,ips,types);
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

	
	/**Methods Name: updateDbUser <br>
	 * Description: 用户账户更新<br>
	 * @author name: liuhao1
	 * @param dbUserModel
	 * @param types
	 * @param ips
	 * @param obj
	 * @return
	 */
	@RequestMapping(value="/authority/{username}",method=RequestMethod.POST)
	public @ResponseBody ResultObject updateUserAuthority(DbUserModel dbUserModel,String types,String ips,ResultObject obj) {
		if(StringUtils.isNullOrEmpty(types) || StringUtils.isNullOrEmpty(ips)) {
			throw new ValidateException("参数不能为空");
		}
		this.dbUserProxy.updateUserAuthority(dbUserModel,ips,types);
		return obj;
	}
	
}
