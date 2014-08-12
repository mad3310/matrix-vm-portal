package com.letv.portal.model;


/**Program Name: HostModel <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月12日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public class HostModel extends BaseModel {
	
	private static final long serialVersionUID = 3497965985607790962L;
	
	private String id;   //主键ID
	private String hostName; //主机名称
	private String hostIp; //主机ip
	private Integer numberOfNodes; //节点个数
	private String hostModel; //主机型号
	private String cpu_model; //cpu型号
	private String memorySize; //内存大小
	private Integer diskSize; //磁盘大小

	private String status; //状态:0:关闭   1:正常
	private String isDeleted; //是否删除   0:无效 1:有效
	private String createTime;
	private String createUser;
	private String updateTime;
	private String updateUser;
	
	
	
	
	
}
