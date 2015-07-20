package com.letv.portal.proxy;

import java.util.Date;
import java.util.List;

import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.monitor.MonitorViewYModel;



public interface IMonitorProxy {
    
	
	/**
	 * Methods Name: collectMclusterServiceData <br>
	 * Description: 搜集集群服务数据任务<br>
	 * @author name: wujun
	 * @return
	 */
	public void collectMclusterServiceData();

	public List<MonitorViewYModel> getMonitorViewData(Long mclusterId, Long chartId,Integer strategy);
	public List<MonitorViewYModel> getDbData(Long serverId,Long chartId, Integer strategy,boolean isTimeAveraging,int format);
	public List<MonitorViewYModel> getData(String type,Long serverId,Long chartId, Integer strategy,boolean isTimeAveraging,int format);

	/**Methods Name: deleteOutData <br>
	 * Description: 删除过时数据：当前时间往前一个月<br>
	 * @author name: liuhao1
	 */
	public void deleteOutData();
	public void deleteOutData(MonitorIndexModel monitorIndexModel,Date date);

	void collectClusterServiceData();
	void collectOSSServiceData();
	
	/**
	  * @Title: addMonitorPartition
	  * @Description: 添加分区（每天24点之前执行一次，添加第二天2个分区0:00-12:00,12:00-24:00）   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月16日 下午6:51:42
	  */
	void addMonitorPartition();
	
	/**
	  * @Title: deleteMonitorPartitionThirtyDaysAgo
	  * @Description: 删除30天以前的分区  
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月17日 上午10:26:53
	  */
	void deleteMonitorPartitionThirtyDaysAgo();
}
