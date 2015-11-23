package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.common.paging.impl.Page;
import com.letv.portal.model.ContainerModel;



/**Program Name: IContainerService <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月22日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IContainerService extends IBaseService<ContainerModel> {
	
	/**Methods Name: findPagebyParams <br>
	 * Description: 根据查询条件查出分页数据<br>
	 * @author name: liuhao1
	 * @param params
	 * @param page
	 * @return
	 */
	public Page findPagebyParams(Map<String,Object> params,Page page);
	
	/**Methods Name: selectByMclusterId <br>
	 * Description: 根据clusterId查出节点<br>
	 * @author name: liuhao1
	 * @param clusterId
	 * @return
	 */
	public List<ContainerModel> selectByMclusterId(Long mclusterId);
	
	/**Methods Name: selectByClusterId <br>
	 * Description: 根据clusterId查出节点<br>
	 * @author name: liuhao1
	 * @param clusterId
	 * @return
	 */
	public List<ContainerModel> selectNormalByClusterId(Long mclusterId);

	public void deleteByMclusterId(Long mclusterId);

	/**Methods Name: updateHostIpByName <br>
	 * Description: 根据名称修改宿主机ip<br>
	 * @author name: liuhao1
	 * @param container
	 */
	public void updateHostIpByName(ContainerModel container);

	/**Methods Name: selectByName <br>
	 * Description: 根据container名称查询container信息<br>
	 * @author name: liuhao1
	 * @param containerName
	 * @return
	 */
	public ContainerModel selectByName(String containerName);
	
	public  List<ContainerModel> selectContainerByMclusterId(Long clusterId);
	public  List<ContainerModel> selectAllByMap(Map<String,Object> map);

	/**Methods Name: selectVips4Monitor <br>
	 * Description: 查询有效的vip节点ip，进行监控预警<br>
	 * @author name: liuhao1
	 * @return
	 */
	public List<ContainerModel> selectVipIps4Monitor();

	/**Methods Name: selectVaildVipContainers <br>
	 * Description: 有效集群的vip节点<br>
	 * @author name: liuhao1
	 * @return
	 */
	public List<ContainerModel> selectVaildVipContainers(Map<String,Object> params);

	ContainerModel selectValidVipContianer(Long mclusterId, String type, Map<String, Object> params);

	List<ContainerModel> selectVaildNormalContainers(Map<String, Object> params);
	
}
