package com.letv.portal.proxy.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.letv.common.util.ConfigUtil;
import com.letv.common.util.PasswordRandom;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.DbUserRoleStatus;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IDbUserService;


@Component
public class DbUserProxyImpl extends BaseProxyImpl<DbUserModel> implements
		IDbUserProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(DbUserProxyImpl.class);

	private final static String DEFAULT_DB_RO_NAME = ConfigUtil.getString("default.db.ro.name");
	
	@Autowired
	private IDbUserService dbUserService;
	@Autowired
	private IBuildTaskService buildTaskService;
	
	@Override
	public IBaseService<DbUserModel> getService() {
		return dbUserService;
	}

	@Override
	public void saveAndBuild(DbUserModel dbUserModel) {
		
		String[] ips = dbUserModel.getAcceptIp().split(",");	
		StringBuffer ids = new StringBuffer();
		String password = PasswordRandom.genStr();
		for (String ip : ips) {
			dbUserModel.setAcceptIp(ip);
			dbUserModel.setPassword(password);
			this.dbUserService.insert(dbUserModel);
			ids.append(dbUserModel.getId()).append(",");
		}
		this.buildTaskService.buildUser(ids.substring(0, ids.length()-1));
	}
	
	public void updateDbUser(DbUserModel dbUserModel){
		this.dbUserService.update(dbUserModel);
		this.buildTaskService.updateUser(dbUserModel.getId().toString());
	}
	
	public void deleteDbUser(String dbUserId){
		this.buildTaskService.deleteDbUser(dbUserId);
		String[] ids = dbUserId.split(",");
		for (String id : ids) {
			DbUserModel dbUserModel = new DbUserModel();
			dbUserModel.setId(Long.parseLong(id));
			this.dbUserService.delete(dbUserModel);
		}	
	}
	public void buildDbUser(String DbUserId){
		this.buildTaskService.buildUser(DbUserId);
	}
	
	@Override
	public List<String> selectIpsFromUser(Long dbId) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("dbId", dbId);
		params.put("username", DEFAULT_DB_RO_NAME);
		
		List<DbUserModel> dbUsers = this.dbUserService.selectByMap(params);
		List<String> ips = new ArrayList<String>();
		for (DbUserModel dbUser : dbUsers) {
			ips.add(dbUser.getAcceptIp());
		}
		return ips;
	}

	@Override
	public void saveOrUpdateIps(Long dbId, String ips) {
		String[] arrs = ips.split(",");
		List<String> newIps = new ArrayList<String>();
		for (String ip : arrs) {
			newIps.add(ip);
		}
		List<String> oldIps = this.selectIpsFromUser(dbId);
		
		List<String> temp = new ArrayList<String>(newIps);
		newIps.removeAll(oldIps); //add ips
		oldIps.removeAll(temp); //remove ips
		
		//add new ips in dbUser
		for (String newIp : newIps) {
			DbUserModel dbUser = new DbUserModel();
			dbUser.setDbId(dbId);
			dbUser.setUsername(DEFAULT_DB_RO_NAME);
			dbUser.setAcceptIp(newIp);
			dbUser.setType(DbUserRoleStatus.RO.getValue());
			dbUser.setDeleted(true);
			dbUser.setMaxConcurrency(50);
			dbUser.setReadWriterRate("2:1");
			dbUser.setStatus(DbStatus.NORMAL.getValue());
			this.dbUserService.insert(dbUser);
		}
		//remove ips in dbUser
		Map<String, Object> params = new HashMap<String,Object>();
		for (String oldIp : oldIps) {
			params.put("dbId", dbId);
			params.put("acceptIp", oldIp);
			List<DbUserModel> dbUsers = this.dbUserService.selectByMap(params);
			for (DbUserModel dbUser : dbUsers) {
				this.dbUserService.delete(dbUser);
			}
		}
		
	}
}
