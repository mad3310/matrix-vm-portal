package com.letv.portal.model;

import java.util.List;

import com.letv.common.model.BaseModel;


/**Program Name: DbModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class DbModel extends BaseModel {
	
	private static final long serialVersionUID = -5881585751828246693L;
	
	private Long mclusterId; //所属cluster
	private Long projectId;
	private String dbName; //db名称
	private Integer status; //状态
	
	private MclusterModel mcluster; //所属cluster

	private String backupCycle;   //备份周期
	private Integer noticeType;   //通知类型
	private String descn;   //描述
	private String developLanguage;   //开发语言
	private Integer engineType;   //存储引擎类型   0:INNOB   1:ISMIY
	private Integer linkType;   //链接类型:    0:长链接   1:短链接
	
	private String fromDbIp;   //原数据库ip
	private String fromDbPort;   //原数据库port
	private String fromDbName;   //原数据库name
	
	private String auditUser;   //审核人
	private String auditInfo;
	private UserModel user;
	
	private Long hclusterId;
	private HclusterModel hcluster;
	
	private List<ContainerModel> containers;
	
	public Long getHclusterId() {
		return hclusterId;
	}
	public void setHclusterId(Long hclusterId) {
		this.hclusterId = hclusterId;
	}
	public UserModel getUser() {
		return user;
	}
	public void setUser(UserModel user) {
		this.user = user;
	}
	public List<ContainerModel> getContainers() {
		return containers;
	}
	public void setContainers(List<ContainerModel> containers) {
		this.containers = containers;
	}
	public Long getMclusterId() {
		return mclusterId;
	}
	public void setMclusterId(Long mclusterId) {
		this.mclusterId = mclusterId;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public MclusterModel getMcluster() {
		return mcluster;
	}
	public void setMcluster(MclusterModel mcluster) {
		this.mcluster = mcluster;
	}
	public String getBackupCycle() {
		return backupCycle;
	}
	public void setBackupCycle(String backupCycle) {
		this.backupCycle = backupCycle;
	}
	public Integer getNoticeType() {
		return noticeType;
	}
	public void setNoticeType(Integer noticeType) {
		this.noticeType = noticeType;
	}
	public String getDescn() {
		return descn;
	}
	public void setDescn(String descn) {
		this.descn = descn;
	}
	public String getDevelopLanguage() {
		return developLanguage;
	}
	public void setDevelopLanguage(String developLanguage) {
		this.developLanguage = developLanguage;
	}
	public Integer getEngineType() {
		return engineType;
	}
	public void setEngineType(Integer engineType) {
		this.engineType = engineType;
	}
	public Integer getLinkType() {
		return linkType;
	}
	public void setLinkType(Integer linkType) {
		this.linkType = linkType;
	}
	public String getFromDbIp() {
		return fromDbIp;
	}
	public void setFromDbIp(String fromDbIp) {
		this.fromDbIp = fromDbIp;
	}
	public String getFromDbPort() {
		return fromDbPort;
	}
	public void setFromDbPort(String fromDbPort) {
		this.fromDbPort = fromDbPort;
	}
	public String getFromDbName() {
		return fromDbName;
	}
	public void setFromDbName(String fromDbName) {
		this.fromDbName = fromDbName;
	}
	public String getAuditUser() {
		return auditUser;
	}
	public void setAuditUser(String auditUser) {
		this.auditUser = auditUser;
	}
	public String getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}
	public HclusterModel getHcluster() {
		return hcluster;
	}
	public void setHcluster(HclusterModel hcluster) {
		this.hcluster = hcluster;
	}
}
