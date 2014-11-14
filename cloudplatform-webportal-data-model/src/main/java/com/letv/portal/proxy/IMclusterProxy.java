package com.letv.portal.proxy;

import com.letv.portal.model.MclusterModel;


/**Program Name: IMclusterProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IMclusterProxy extends IBaseProxy<MclusterModel> {
	
	/**Methods Name: isUniqueByName <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param mclusterName
	 * @return
	 */
	public Boolean isExistByName(String mclusterName);
	
	/**Methods Name: inertAndBuild <br>
	 * Description: 保存mcluster 并创建container集群 <br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 */
	public void insertAndBuild(MclusterModel mclusterModel);

	/**Methods Name: deleteAndRemove <br>
	 * Description:	删除container集群，并移除物理机上相应container <br>
	 * @author name: liuhao1
	 * @param mclusterId
	 */
	public void deleteAndRemove(Long mclusterId);

	/**Methods Name: start <br>
	 * Description: 启动container集群<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 */
	public void start(Long mclusterId);

	/**Methods Name: stop <br>
	 * Description: 停止container集群<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 */
	public void stop(Long mclusterId);
	
	/**Methods Name: checkStatus <br>
	 * Description: 检查所有container集群状态<br>
	 * @author name: liuhao1
	 */
	public void checkStatus();
	
	/**Methods Name: checkCount <br>
	 * Description: 检查container集群数量<br>
	 * @author name: liuhao1
	 */
	public void checkCount();
	
}