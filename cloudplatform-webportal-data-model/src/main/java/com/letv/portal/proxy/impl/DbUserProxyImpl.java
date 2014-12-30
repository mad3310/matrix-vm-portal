package com.letv.portal.proxy.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.letv.common.exception.ValidateException;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.PasswordRandom;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.proxy.IDbUserProxy;
import com.letv.portal.python.service.IBuildTaskService;
import com.letv.portal.service.IBaseService;
import com.letv.portal.service.IDbUserService;
import com.mysql.jdbc.StringUtils;


@Component
public class DbUserProxyImpl extends BaseProxyImpl<DbUserModel> implements
		IDbUserProxy{
	
	private final static Logger logger = LoggerFactory.getLogger(DbUserProxyImpl.class);

	@Autowired
	private IDbUserService dbUserService; 
	@Autowired
	private IBuildTaskService buildTaskService;
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	@Value("${default.db.ro.name}")
	private String DEFAULT_DB_RO_NAME;
	
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
	
	@Override
	public void saveAndBuild(List<DbUserModel> users) {
		if(users.isEmpty()) {
			throw new ValidateException("参数不能为空：users is null");
		}
		StringBuffer ids = new StringBuffer();
		for (DbUserModel dbUser : users) {
			dbUser.setCreateUser(sessionService.getSession().getUserId());
			this.dbUserService.insert(dbUser);
			ids.append(dbUser.getId()).append(",");
		}
		this.buildTaskService.buildUser(ids.substring(0, ids.length()-1));
	}
	
	public void updateDbUser(DbUserModel dbUserModel){
		this.dbUserService.update(dbUserModel);
		this.buildTaskService.updateUser(dbUserModel.getId().toString());
	}
	
	@Override
	public void saveAndBuild(DbUserModel dbUserModel, String ips, String types) {
		if(StringUtils.isNullOrEmpty(ips) || StringUtils.isNullOrEmpty(types) || null == dbUserModel) {
			throw new ValidateException("参数不能为空");
		}
		List<DbUserModel> users = this.transToDbUser(dbUserModel, ips, types);
		this.saveAndBuild(users);
	}
	
	@Override
	public void updateUserAuthority(DbUserModel dbUserModel, String ips, String types) {
		if(StringUtils.isNullOrEmpty(ips) || StringUtils.isNullOrEmpty(types) || null == dbUserModel) {
			throw new ValidateException("参数不能为空");
		}
		List<String> arryIps = Arrays.asList(ips.split(","));
		List<String> arryTypes = Arrays.asList(types.split(","));
		List<String> formIps = new ArrayList<String>(arryIps);
		List<String> formTypes = new ArrayList<String>(arryTypes);
		
		List<DbUserModel> adds = new ArrayList<DbUserModel>();
		List<DbUserModel> removes = new ArrayList<DbUserModel>();
		List<DbUserModel> updates = new ArrayList<DbUserModel>();
		
		List<DbUserModel> oldUsers = this.dbUserService.selectByDbIdAndUsername(dbUserModel.getDbId(), dbUserModel.getUsername());
		boolean flag = true;
		String pwd = StringUtils.isNullOrEmpty(dbUserModel.getPassword())?PasswordRandom.genStr():dbUserModel.getPassword();
		for (DbUserModel dbUser : oldUsers) {
			String ip = dbUser.getAcceptIp();
			for (int i = 0; i < arryIps.size(); i++) {
				if(ip.equals(arryIps.get(i))) {
					int formType = Integer.parseInt(arryTypes.get(i));
					//fix password reset start
					dbUser.setType(formType);
					dbUser.setPassword(pwd);
					updates.add(dbUser);
					formIps.remove(arryIps.get(i));
					formTypes.remove(arryTypes.get(i));
					//fix password reset end
					flag = false;
					break;
				}
			}
			if(flag) {
				//都不相同，删除。继续遍历
				removes.add(dbUser);
			}
			flag = true;
		}
		dbUserModel.setPassword(pwd);
		//剩余的，新增。
		adds = this.transToDbUser(dbUserModel, formIps, formTypes);
		
		//分别操作 新增、修改、删除。
		if(!adds.isEmpty()) {
			this.saveAndBuild(adds);
		}
		if(!updates.isEmpty()) {
			this.updateDbUser(updates);
		}
		if(!removes.isEmpty()) {
			this.deleteAndBuild(removes);
		}
	}
	
	@Override
	public void updateDbUser(List<DbUserModel> users) {
		for (DbUserModel dbUser : users) {
			this.updateDbUser(dbUser);
		}
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
	
	@Override
	public void deleteAndBuild(List<DbUserModel> users) {
		if(users.isEmpty()) {
			throw new ValidateException("参数不能为空：users is null");
		}
		StringBuffer ids = new StringBuffer();
		for (DbUserModel dbUserModel : users) {
			ids.append(dbUserModel.getId()).append(",");
		}
		this.deleteDbUser(ids.substring(0, ids.length()-1));
		
	}
	
	public void buildDbUser(String DbUserId){
		this.buildTaskService.buildUser(DbUserId);
	}
	
	private List<DbUserModel> transToDbUser(DbUserModel dbUserModel,String ips,String types) {
		List<DbUserModel> users = new ArrayList<DbUserModel>();
		if(StringUtils.isNullOrEmpty(ips) || StringUtils.isNullOrEmpty(types)) {
			return users;
		}
		String[] arryIps = ips.split(",");
		String[] arryTypes = types.split(",");
		return this.transToDbUser(dbUserModel, arryIps, arryTypes);
	}
	private List<DbUserModel> transToDbUser(DbUserModel dbUserModel,String[] ips,String[] types) {
		List<DbUserModel> users = new ArrayList<DbUserModel>();
		for (int i = 0; i < ips.length; i++) {
			DbUserModel dbUser = new DbUserModel();
			BeanUtils.copyProperties(dbUserModel, dbUser);
			dbUser.setAcceptIp(ips[i]);
			dbUser.setType(Integer.parseInt(types[i]));
			users.add(dbUser);
		}
		return users;
	}
	private List<DbUserModel> transToDbUser(DbUserModel dbUserModel,List<String> ips,List<String> types) {
		List<DbUserModel> users = new ArrayList<DbUserModel>();
		for (int i = 0; i < ips.size(); i++) {
			DbUserModel dbUser = new DbUserModel();
			BeanUtils.copyProperties(dbUserModel, dbUser);
			dbUser.setAcceptIp(ips.get(i));
			dbUser.setType(Integer.parseInt(types.get(i)));
			users.add(dbUser);
		}
		return users;
	}
	
	
}
