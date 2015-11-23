package com.letv.portal.dao;

import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.ContainerModel;

/**Program Name: IContainerDao <br>
 * Description:  <br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年8月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IContainerDao extends IBaseDao<ContainerModel> {
	
	
	
	/**Methods Name: selectByClusterId <br>
	 * Description: 根据cluster 查出N个节点<br>
	 * @author name: liuhao1
	 * @param clusterId
	 * @return
	 */
	public List<ContainerModel> selectByMclusterId(Long clusterId);
	public void deleteByMclusterId(Long mclusterId);
	public void updateHostIpByName(ContainerModel container);
	public ContainerModel selectByName(String containerName);
	public  List<ContainerModel> selectContainerByMclusterId(Long clusterId);
	public  List<ContainerModel> selectAllByMap(Map map);
	public List<ContainerModel> selectVipIps4Monitor();
}
