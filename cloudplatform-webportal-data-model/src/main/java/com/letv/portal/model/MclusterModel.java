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
	
	private String id;   //主键ID
	private String name; //名称
	
	private String status; //状态： 0:已创建 1:启动 2:关闭 3:异常
	private String isDeleted; //是否删除   0:无效 1:有效
	private String createTime;
	private String createUser;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Override
	public String toString() {
		return "MclusterModel [id=" + id + ", name=" + name + ", status="
				+ status + ", isDeleted=" + isDeleted + ", createTime="
				+ createTime + ", createUser=" + createUser + "]";
	}
	
	
	
}
