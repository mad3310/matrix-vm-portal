package com.letv.portal.proxy;

import java.util.List;

import com.letv.portal.model.ContainerModel;

/**Program Name: IContainerProxy <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年10月7日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IContainerProxy extends IBaseProxy<ContainerModel> {
	
	/**Methods Name: start <br>
	 * Description: 启动container<br>
	 * @author name: liuhao1
	 * @param containerId
	 */
	public void start(Long containerId);

	/**Methods Name: stop <br>
	 * Description: 停止container<br>
	 * @author name: liuhao1
	 * @param containerId
	 */
	public void stop(Long containerId);

	/**Methods Name: checkStatus <br>
	 * Description: 检查container状态<br>
	 * @author name: liuhao1
	 */
	public void checkStatus();

	/**Methods Name: selectByMclusterId <br>
	 * Description: 查询container集群数据<br>
	 * @author name: liuhao1
	 * @param mclusterId
	 * @return
	 */
	public List<ContainerModel> selectByMclusterId(Long mclusterId);
	public  List<ContainerModel> selectContainerByMclusterId(Long clusterId);
}
