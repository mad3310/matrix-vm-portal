package com.letv.portal.model;

import org.springframework.beans.factory.annotation.Value;

import com.letv.common.model.BaseModel;


/**Program Name: DbUserModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class DbUserModel extends BaseModel {
	
	
	
	private static final long serialVersionUID = 549601154139114218L;
	
	private Long dbId; //所属db
	private String username; //用户名
	private String password; //密码
	private String salt; //加密盐
	private Integer type; //用户类型   1管理员 2读用户3 读写用户
	private String acceptIp;         //用户访问IP(192.178.2.1或192.168.3.%)
	private String readWriterRate;   // 读写比例(1:2)
	private Integer maxConcurrency;   //最大并发量(100/s)
	private Integer status; 
	private String descn; 
	
	private Integer maxQueriesPerHour;   //每小时最大查询数(用户可填,系统自动填充,管理员审核修改)
	private Integer maxUpdatesPerHour;   //每小时最大更新数(用户可填,系统自动填充,管理员审核修改)
	private Integer maxConnectionsPerHour;   //每小时最大连接数(用户可填,系统自动填充,管理员审核修改)
	private Integer maxUserConnections;   //用户最大链接数(用户可填,系统自动填充,管理员审核修改)
	
	@Value("${default.db.ro.name}")
	private String name4Ip; //ip维护name
	
	private DbModel db;

	public Long getDbId() {
		return dbId;
	}

	public void setDbId(Long dbId) {
		this.dbId = dbId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAcceptIp() {
		return acceptIp;
	}

	public void setAcceptIp(String acceptIp) {
		this.acceptIp = acceptIp;
	}

	public String getReadWriterRate() {
		return readWriterRate;
	}

	public void setReadWriterRate(String readWriterRate) {
		this.readWriterRate = readWriterRate;
	}

	public Integer getMaxConcurrency() {
		return maxConcurrency;
	}

	public void setMaxConcurrency(Integer maxConcurrency) {
		this.maxConcurrency = maxConcurrency;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getMaxQueriesPerHour() {
		return maxQueriesPerHour;
	}

	public void setMaxQueriesPerHour(Integer maxQueriesPerHour) {
		this.maxQueriesPerHour = maxQueriesPerHour;
	}

	public Integer getMaxUpdatesPerHour() {
		return maxUpdatesPerHour;
	}

	public void setMaxUpdatesPerHour(Integer maxUpdatesPerHour) {
		this.maxUpdatesPerHour = maxUpdatesPerHour;
	}

	public Integer getMaxConnectionsPerHour() {
		return maxConnectionsPerHour;
	}

	public void setMaxConnectionsPerHour(Integer maxConnectionsPerHour) {
		this.maxConnectionsPerHour = maxConnectionsPerHour;
	}

	public Integer getMaxUserConnections() {
		return maxUserConnections;
	}

	public void setMaxUserConnections(Integer maxUserConnections) {
		this.maxUserConnections = maxUserConnections;
	}

	public DbModel getDb() {
		return db;
	}

	public void setDb(DbModel db) {
		this.db = db;
	}

	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	public String getName4Ip() {
		return name4Ip;
	}

	public void setName4Ip(String name4Ip) {
		this.name4Ip = name4Ip;
	}

}
