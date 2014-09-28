package com.letv.portal.model;

/**Program Name: MclusterModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class MclusterModel extends BaseModel {
	
	
	private static final long serialVersionUID = 8873122802478974943L;
	
	private String mclusterName; //名称
	
	private String adminUser;
	private String adminPassword;
	
	private String status; //状态：

	
	public MclusterModel(){};
	public MclusterModel(String mclusterName,String status){
		this.mclusterName = mclusterName;
		this.status = status;
	}
	public MclusterModel(String status){
		this.status = status;
	}
	
	
	public String getMclusterName() {
		return mclusterName;
	}
	public void setMclusterName(String mclusterName) {
		this.mclusterName = mclusterName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getAdminUser() {
		return adminUser;
	}
	public void setAdminUser(String adminUser) {
		this.adminUser = adminUser;
	}
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}
	@Override
	public String toString() {
		return "MclusterModel [mclusterName=" + mclusterName
				+ ", adminUser=" + adminUser + ", adminPassword="
				+ adminPassword + ", status=" + status
				+ "]";
	}
	
	
	
}
