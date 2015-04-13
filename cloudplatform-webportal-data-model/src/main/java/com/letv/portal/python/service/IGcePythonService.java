package com.letv.portal.python.service;

import java.util.Map;

import com.letv.common.util.HttpClient;
import com.letv.portal.model.DbUserModel;
import com.letv.portal.model.HostModel;


/**Program Name: IGcePythonService <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年3月31日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IGcePythonService {
	
	/**Methods Name: createContainer <br>
	 * Description: 创建container，执行一次,传入mclusterName<br>
	 * @author name: liuhao1
	 * @param mclusterName
	 * @return
	 */
	public String createContainer(Map<String,String> params,String ip,String username,String password);
	
	/**Methods Name: checkContainerCreateStatus <br>
	 * Description: 检查container创建状态,通过检查策略进行检查<br>
	 * @author name: liuhao1
	 * @return
	 */
	public String checkContainerCreateStatus(String mclusterName,String ip,String username,String password);
	
	/**Methods Name: initZookeeper <br>
	 * Description: 初始化zookeeper节点<br>
	 * @author name: liuhao1
	 * @param nodeIp 初始化的ip地址
	 * @return
	 */

	public String initZookeeper(String nodeIp,String port);
	
	/**Methods Name: initUserAndPwd4Manager <br>
	 * Description: 初始化mcluster管理用户名密码<br>
	 * @author name: liuhao1
	 * @param nodeIp 初始化的ip地址
	 * @param username 集群用户名
	 * @param password 集群密码
	 * @return
	 */
	public String initUserAndPwd4Manager(String nodeIp,String port,String username,String password);
	
	public String createContainer1(Map<String,String> params,String ip,String port,String username,String password);
	public String createContainer2(Map<String,String> params,String ip,String port,String username,String password);

	public String syncContainer2(Map<String,String> params,String nodeIp1,String port,String adminUser, String adminPassword);

	public String startCluster(String nodeIp1,String port,String adminUser,String adminPassword);
	public String CheckClusterStatus(String nodeIp1,String port,String adminUser,String adminPassword);
	
	public String nginxProxyConfig(Map<String,String> params,String ip,String port,String username,String password);
	
	public String start(Map<String, String> params, String nodeIp1,String adminUser, String adminPassword);
	public String stop(Map<String, String> params, String nodeIp1,String adminUser, String adminPassword);
	public String restart(Map<String, String> params, String nodeIp1,String adminUser, String adminPassword);
	public String checkStatus(String nodeIp1,String adminUser, String adminPassword);
}
