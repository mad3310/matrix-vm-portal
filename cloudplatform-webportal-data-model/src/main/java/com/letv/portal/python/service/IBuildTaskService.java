package com.letv.portal.python.service;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.ContainerModel;
import com.letv.portal.model.ContainerMonitorModel;
import com.letv.portal.model.HostModel;
import com.letv.portal.model.MclusterModel;
import com.letv.portal.model.MonitorDetailModel;


/**Program Name: IBuildTaskService <br>
 * Description:  mcluster db等创建调度<br>
 * @author name: liuhao1 <br>
 * Written Date: 2014年9月14日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IBuildTaskService { 
	
	/**Methods Name: buildMcluster <br>
	 * Description: 创建mcluster,并创建db<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 */
	public void buildMcluster(MclusterModel mclusterModel,Long dbId);
	
	/**Methods Name: buildMcluster <br>
	 * Description: 创建mcluster<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 */
	public void buildMcluster(MclusterModel mclusterModel);

	/**Methods Name: buildUser <br>
	 * Description: 创建用户<br>
	 * @author name: liuhao1
	 * @param ids
	 */
	public void buildUser(String ids);
	/**Methods Name: updateUser <br> 
	 * Description: 修改用户<br>
	 * @author name: liuhao1
	 * @param ids
	 */
	public void updateUser(String ids);

	/**Methods Name: buildDb <br>
	 * Description: 创建数据库<br>
	 * @author name: liuhao1
	 * @param dbId
	 */
	public void buildDb(Long dbId);
	
	/**Methods Name: createContainer <br>
	 * Description: 创建container<br>
	 * @author name: liuhao1
	 * @param mclusterModel
	 * @return
	 */
	public boolean createContainer(MclusterModel mclusterModel,Long dbId,HostModel host);
	
	/**Methods Name: initContainer <br>
	 * Description: 初始化contianer,组合各分步骤<br>
	 * @author name: liuhao1
	 * @param params
	 * @return
	 */
	public boolean initContainer(MclusterModel mclusterModel,Long dbId);
	
	/**Methods Name: removeMcluster <br>
	 * Description: <br>
	 * @author name: liuhao1
	 * @param mcluster
	 */
	public void removeMcluster(MclusterModel mcluster);
	/**
	 * Methods Name: deleteUser <br>
	 * Description: 删除 DbUser<br>
	 * @author name: wujun
	 * @param ids 多个dbUserId
	 */
	public void deleteDbUser(String ids);

	/**Methods Name: startMcluster <br>
	 * Description: 启动container集群<br>
	 * @author name: liuhao1
	 * @param mcluster
	 */
	public void startMcluster(MclusterModel mcluster);

	/**Methods Name: stopMcluster <br>
	 * Description: 停止container集群<br>
	 * @author name: liuhao1
	 * @param mcluster
	 */
	public void stopMcluster(MclusterModel mcluster);

	/**Methods Name: startContainer <br>
	 * Description: 启动单个container<br>
	 * @author name: liuhao1
	 * @param container
	 */
	public void startContainer(ContainerModel container);

	/**Methods Name: stopContainer <br>
	 * Description: 停止单个container<br>
	 * @author name: liuhao1
	 * @param container
	 */
	public void stopContainer(ContainerModel container);

	/**Methods Name: checkMclusterStatus <br>
	 * Description: 检查container集群状态<br>
	 * @author name: liuhao1
	 * @param mcluster
	 */
	public void checkMclusterStatus(MclusterModel mcluster);
	
	/**Methods Name: checkContainerStatus <br>
	 * Description: 检查container集群<br>
	 * @author name: liuhao1
	 * @param container
	 */
	public void checkContainerStatus(ContainerModel container);
	/**
	 * Methods Name: createHost <br>
	 * Description: 创建host<br>
	 * @author name: wujun
	 * @param hostModel
	 * @return
	 */
	public void createHost(HostModel hostModel);

	/**Methods Name: checkMclusterCount <br>
	 * Description: 检查container集群数量一致性<br>
	 * @author name: liuhao1
	 */
	public void checkMclusterCount();
    /**
     * Methods Name: getMonitorData <br>
     * Description: 获得集群的监控数据<br>
     * @author name: wujun
     * @param ips
     * @return
     */
	public List<ContainerMonitorModel> getMonitorData(List<ContainerModel> containerModels);
	/**
	 * Methods Name: getMonitorDetailNodeAndDbData <br>
	 * Description: 集群监控详细信息展示<br>
	 * @author name: wujun
	 * @param ip
	 * @return
	 */
	public ContainerMonitorModel getMonitorDetailNodeAndDbData(String ip,String mclusterName);	
	/**
	 * Methods Name: getMonitorDetailClusterData <br>
	 * Description: 集群监控主要信息展示<br>
	 * @author name: wujun
	 * @param ip
	 * @return
	 */
	public ContainerMonitorModel getMonitorDetailClusterData(String ip);
    
    public  Map getContainerServiceData(String ip,String index)throws Exception;
}
