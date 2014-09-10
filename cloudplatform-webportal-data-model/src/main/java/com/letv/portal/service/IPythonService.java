package com.letv.portal.service;

/**Program Name: IPythonService <br>
 * Description:  与底层python rest交互接口<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月10日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */

public interface IPythonService {
	
	/**Methods Name: createContainer <br>
	 * Description: 创建container，执行一次，传入四组节点数据<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String createContainer();
	
	/**Methods Name: checkContainerCreateStatus <br>
	 * Description: 检查container创建状态,通过检查策略进行检查<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String checkContainerCreateStatus();
	
	/**Methods Name: initZookeeper <br>
	 * Description: 初始化zookeeper节点<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String initZookeeper();
	
	/**Methods Name: initUserAndPwd4Manager <br>
	 * Description: 初始化mcluster管理用户名密码<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String initUserAndPwd4Manager();
	
	/**Methods Name: postMclusterInfo <br>
	 * Description: 提交mcluster集群信息<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String postMclusterInfo();
	
	/**Methods Name: initMcluster <br>
	 * Description: 初始化集群<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String initMcluster();

	/**Methods Name: postContainerInfo <br>
	 * Description: 提交节点信息<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String postContainerInfo();
	
	/**Methods Name: syncContainer <br>
	 * Description: 同步节点信息<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String syncContainer();
	
	/**Methods Name: startMcluster <br>
	 * Description: 启动集群<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String startMcluster();
	
	/**Methods Name: checkContainerStatus <br>
	 * Description: 检查集群状态：检查节点状态<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String checkContainerStatus();
	
	/**Methods Name: createDb <br>
	 * Description: 创建数据库<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String createDb();
	
	/**Methods Name: createDbManagerUser <br>
	 * Description: 创建管理用户<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String createDbManagerUser();
	
	/**Methods Name: createDbRoDbUser <br>
	 * Description: 创建只读用户<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String createDbRoDbUser();
	
	/**Methods Name: createDbWrUser <br>
	 * Description: 创建读写用户<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String createDbWrUser(); 
	
	
	
	//组合功能
	
	/**Methods Name: initContainer <br>
	 * Description: 初始化contianer,组合各分步骤<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String initContainer();
}
