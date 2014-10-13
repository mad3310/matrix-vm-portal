package com.letv.portal.model;

import com.letv.common.model.BaseModel;

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
	private Integer status; //状态
	
	private UserModel createUserModel;
	
	public String getMclusterName() {
		return mclusterName;
	}
	public void setMclusterName(String mclusterName) {
		this.mclusterName = mclusterName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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
	
	public UserModel getCreateUserModel() {
		return createUserModel;
	}
	public void setCreateUserModel(UserModel createUserModel) {
		this.createUserModel = createUserModel;
	}
	@Override
	public String toString() {
		return "MclusterModel [mclusterName=" + mclusterName + ", status="
				+ status + ", adminUser=" + adminUser + ", adminPassword="
				+ adminPassword + "]";
	}

}
