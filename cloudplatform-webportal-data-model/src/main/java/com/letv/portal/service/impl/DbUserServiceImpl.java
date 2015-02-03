package com.letv.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.letv.common.dao.IBaseDao;
import com.letv.common.dao.QueryParam;
import com.letv.common.email.ITemplateMessageSender;
import com.letv.common.paging.impl.Page;
import com.letv.common.session.SessionServiceImpl;
import com.letv.common.util.PasswordRandom;
import com.letv.portal.dao.IDbUserDao;
import com.letv.portal.enumeration.DbStatus;
import com.letv.portal.enumeration.DbUserRoleStatus;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.service.IDbUserService;
import com.mysql.jdbc.StringUtils;


@Service("dbUserService")
public class DbUserServiceImpl extends BaseServiceImpl<DbUserModel> implements
		IDbUserService{
	
	private final static Logger logger = LoggerFactory.getLogger(DbUserServiceImpl.class);
	
	@Resource
	private IDbUserDao dbUserDao;
	
	@Value("${error.email.to}")
	private String ERROR_MAIL_ADDRESS;
	
	@Autowired
	private ITemplateMessageSender defaultEmailSender;
	
	@Value("${default.db.ro.name}")
	private String DEFAULT_DB_RO_NAME;
	
	@Autowired(required=false)
	private SessionServiceImpl sessionService;
	
	public DbUserServiceImpl() {
		super(DbUserModel.class);
	}
	
	@Override
	public IBaseDao<DbUserModel> getDao() {
		return dbUserDao;
	}
	
	@Override
	public List<DbUserModel> selectByDbId(Long dbId) {
		return this.dbUserDao.selectByDbId(dbId);
	}

	@Override
	public Page findPagebyParams(Map<String, Object> params, Page page) {
		QueryParam param = new QueryParam(params,page);
		page.setData(this.dbUserDao.selectPageByMap(param));
		page.setTotalRecords(this.dbUserDao.selectByMapCount(params));
		return page;
	}

	@Override
	public Map<String, Object> selectCreateParams(Long id) {
		return this.dbUserDao.selectCreateParams(id);
	}

	@Override
	public void updateStatus(DbUserModel dbUserModel) {
		this.dbUserDao.updateStatus(dbUserModel);
	}
	
	@Override
	public List<DbUserModel> selectByIpAndUsername(DbUserModel dbUserModel){
		return this.dbUserDao.selectByIpAndUsername(dbUserModel);
	}
	
	@Override
	public void insert(DbUserModel dbUserModel){
		
		
		//计算相关参数
		int maxQueriesPerHour = 1000;   //每小时最大查询数(用户可填,系统自动填充,管理员审核修改)
		int maxUpdatesPerHour=1000;   //每小时最大更新数(用户可填,系统自动填充,管理员审核修改)
		int maxConnectionsPerHour=100;   //每小时最大连接数(用户可填,系统自动填充,管理员审核修改)
		int maxUserConnections=10;   //用户最大链接数(用户可填,系统自动填充,管理员审核修改)
		
		int maxConcurrency = dbUserModel.getMaxConcurrency();
		
		if( DbUserRoleStatus.MANAGER.getValue() != dbUserModel.getType()) {
			maxUserConnections = maxConcurrency;
			maxConnectionsPerHour = maxConcurrency*2*60*60;
			maxQueriesPerHour = maxConcurrency*2*60*60;
			maxUpdatesPerHour = maxConcurrency*60*60;
		} 
		dbUserModel.setMaxUserConnections(maxUserConnections);
		dbUserModel.setMaxConnectionsPerHour(maxConnectionsPerHour);
		dbUserModel.setMaxQueriesPerHour(maxQueriesPerHour);
		dbUserModel.setMaxUpdatesPerHour(maxUpdatesPerHour);
		
		super.insert(dbUserModel);
	}
	@Override
	public void update(DbUserModel dbUserModel) {
		//计算相关参数
		int maxQueriesPerHour = 1000;   //每小时最大查询数(用户可填,系统自动填充,管理员审核修改)
		int maxUpdatesPerHour=1000;   //每小时最大更新数(用户可填,系统自动填充,管理员审核修改)
		int maxConnectionsPerHour=100;   //每小时最大连接数(用户可填,系统自动填充,管理员审核修改)
		int maxUserConnections=10;   //用户最大链接数(用户可填,系统自动填充,管理员审核修改)
		
		int maxConcurrency = dbUserModel.getMaxConcurrency();
		
		if( DbUserRoleStatus.MANAGER.getValue() != dbUserModel.getType()) {
			maxUserConnections = maxConcurrency;
			maxConnectionsPerHour = maxConcurrency*2*60*60;
			maxQueriesPerHour = maxConcurrency*2*60*60;
			maxUpdatesPerHour = maxConcurrency*60*60;
		} 
		dbUserModel.setMaxUserConnections(maxUserConnections);
		dbUserModel.setMaxConnectionsPerHour(maxConnectionsPerHour);
		dbUserModel.setMaxQueriesPerHour(maxQueriesPerHour);
		dbUserModel.setMaxUpdatesPerHour(maxUpdatesPerHour);
		super.update(dbUserModel);
	}
	public void insertDbUserAndAcceptIp(DbUserModel dbUserModel){
		String[] ips = dbUserModel.getAcceptIp().split(",");		
		for (String ip : ips) {
			dbUserModel.setAcceptIp(ip);
			this.dbUserDao.insert(dbUserModel);
		}
	}
	/**
	 * Methods Name: updateDbUser <br>
	 * Description: 修改dbUser信息<br>
	 * @author name: wujun
	 * @param dbUserModel
	 */
	public void updateDbUser(DbUserModel dbUserModel){
		this.dbUserDao.update(dbUserModel);
	}
	/**
	 * Methods Name: deleteDbUser <br>
	 * Description: 删除dbUser信息<br>
	 * @author name: wujun
	 * @param dbUserModel
	 */
	public void deleteDbUser(String dbUserId){
		String[] ids = dbUserId.split(",");
		for (String id : ids) {
			DbUserModel dbUserModel = new DbUserModel();
			dbUserModel.setId(Long.parseLong(id));
			this.dbUserDao.delete(dbUserModel);
		}	
	}

	@Override
	public void deleteByDbId(Long dbId) {
		this.dbUserDao.deleteByDbId(dbId);
		
	}
	/**
	 * Methods Name: buildDbUser <br>
	 * Description: 审批DbUser<br>
	 * @author name: wujun
	 * @param dbUserId
	 */
	public void buildDbUser(String dbUserId){
		DbUserModel dbUserModel = new DbUserModel();
		dbUserModel.setId(Long.parseLong(dbUserId));
		this.dbUserDao.update(dbUserModel);
	}

	@Override
	public List<DbUserModel> selectGroupByName(Map<String, Object> params) {
		return this.dbUserDao.selectGroupByName(params);
	}
	
	@Override
	public List<String> selectIpsFromUser(Long dbId) {
		List<DbUserModel> dbUsers = this.selectIpsByDbIdAndUsername(dbId, DEFAULT_DB_RO_NAME);
		List<String> ips = new ArrayList<String>();
		for (DbUserModel dbUser : dbUsers) {
			ips.add(dbUser.getAcceptIp());
		}
		return ips;
	}

	@Override
	public List<Map<String,Object>> selectMarkIps4dbUser(Long dbId,String username) {
		List<String> all =  this.selectIpsFromUser(dbId);
		
		List<Map<String,Object>> selected = new ArrayList<Map<String,Object>>();
		
		if(!StringUtils.isNullOrEmpty(username)) {
			List<DbUserModel> dbUsers = this.selectByDbIdAndUsername(dbId, username);
			for (DbUserModel dbUser : dbUsers) {
				Map<String,Object> data = new HashMap<String,Object>();
				String ip = dbUser.getAcceptIp();
				data.put("addr",ip);
				data.put("type", dbUser.getType());
				data.put("used", 1);
				selected.add(data);
				
				if(all.contains(ip)) {
					all.remove(ip);
				}
			}
		}
		for (String ip : all) { //未使用ip
			Map<String,Object> data = new HashMap<String,Object>();
			data.put("addr",ip);
			data.put("used", 0);
			selected.add(data);
		}
		return selected;
	}

	@Override
	public List<DbUserModel> selectByDbIdAndUsername(Long dbId, String username) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("dbId", dbId);
		params.put("username", username);
		List<DbUserModel> dbUsers = this.selectByMap(params);
		return dbUsers;
	}
	
	private List<DbUserModel> selectIpsByDbIdAndUsername(Long dbId, String name4Ip) {
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("dbId", dbId);
		params.put("name4Ip",DEFAULT_DB_RO_NAME);
		List<DbUserModel> dbUsers = this.selectByMap(params);
		return dbUsers;
	}
	
	@Override
	public void createDefalutAdmin(Long dbId){
		DbUserModel dbUserModel = new DbUserModel();
		dbUserModel.setDbId(dbId);
		dbUserModel.setUsername("admin");
		dbUserModel.setPassword(PasswordRandom.genStr());
		dbUserModel.setAcceptIp("%");
     	dbUserModel.setType(DbUserRoleStatus.MANAGER.getValue());
		dbUserModel.setMaxConcurrency(50);
		dbUserModel.setReadWriterRate("2:1");
		dbUserModel.setCreateUser(sessionService.getSession().getUserId());
		this.insert(dbUserModel);
	}

	@Override
	public void updateDescnByUsername(DbUserModel dbUserModel) {
		this.dbUserDao.updateDescnByUsername(dbUserModel);
		
	}
}
