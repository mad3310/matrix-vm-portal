package com.letv.portal.model;


/**Program Name: DbModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class DbModel extends BaseModel {
	
	
	private static final long serialVersionUID = -5881585751828246693L;
	
	private String dbName; //db名称
	private String clusterId; //所属cluster
	private MclusterModel cluster; //所属cluster

	private String status; //状态
	private String auditInfo;
	
	public DbModel(){}
	public DbModel(String status){
		this.status = status;
	}
	public DbModel(String status,String clusterId,String auditInfo){
		this.status = status;
		this.clusterId = clusterId;
		this.auditInfo = auditInfo;
	}
	
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getClusterId() {
		return clusterId;
	}
	public void setClusterId(String clusterId) {
		this.clusterId = clusterId;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public MclusterModel getCluster() {
		return cluster;
	}
	public void setCluster(MclusterModel cluster) {
		this.cluster = cluster;
	}
	
	public String getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(String auditInfo) {
		this.auditInfo = auditInfo;
	}
	@Override
	public String toString() {
		return "DbModel [dbName=" + dbName + ", clusterId="
				+ clusterId + ", cluster=" + cluster + ", status=" + status
				+ "]";
	}
	
	
}
