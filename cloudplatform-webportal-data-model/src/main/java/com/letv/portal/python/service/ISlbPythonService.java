package com.letv.portal.python.service;

import java.util.Map;

import com.letv.common.result.ApiResultObject;


/**Program Name: IGcePythonService <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2015年3月31日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface ISlbPythonService {
	
	/**Methods Name: createContainer <br>
	 * Description: 创建container，执行一次,传入mclusterName<br>
	 * @author name: liuhao1
	 * @param mclusterName
	 * @return
	 */
	public ApiResultObject createContainer(Map<String,String> params,String ip,String username,String password);
	
	/**Methods Name: checkContainerCreateStatus <br>
	 * Description: 检查container创建状态,通过检查策略进行检查<br>
	 * @author name: liuhao1
	 * @return
	 */
	public ApiResultObject checkContainerCreateStatus(String mclusterName,String ip,String username,String password);
	
	/**Methods Name: initZookeeper <br>
	 * Description: 初始化zookeeper节点<br>
	 * @author name: liuhao1
	 * @param nodeIp 初始化的ip地址
	 * @return
	 */

	public ApiResultObject initZookeeper(String nodeIp,Map<String,String> params);
	
	/**Methods Name: initUserAndPwd4Manager <br>
	 * Description: 初始化mcluster管理用户名密码<br>
	 * @author name: liuhao1
	 * @param nodeIp 初始化的ip地址
	 * @param username 集群用户名
	 * @param password 集群密码
	 * @return
	 */
	public ApiResultObject initUserAndPwd4Manager(String nodeIp,String username,String password);
	
	public ApiResultObject createContainer1(Map<String,String> params,String ip,String username,String password);
	public ApiResultObject createContainer2(Map<String,String> params,String ip,String username,String password);

	public ApiResultObject syncContainer2(Map<String,String> params,String nodeIp1,String adminUser, String adminPassword);

	public ApiResultObject startCluster(String nodeIp1,String adminUser,String adminPassword);
	public ApiResultObject CheckClusterStatus(String nodeIp1,String adminUser,String adminPassword);
	
	public ApiResultObject getVipIp(Map<String,String> params,String nodeIp1,String adminUser,String adminPassword);
	
	public ApiResultObject commitProxyConfig(Map<String,String> params,String nodeIp1,String adminUser,String adminPassword);
	public ApiResultObject start(Map<String,String> params,String nodeIp1,String adminUser,String adminPassword);
	public ApiResultObject stop(Map<String,String> params,String nodeIp1,String adminUser,String adminPassword);
	public ApiResultObject restart(Map<String,String> params,String nodeIp1,String adminUser,String adminPassword);
	public ApiResultObject checkStatus(String nodeIp1,String adminUser,String adminPassword);

}
