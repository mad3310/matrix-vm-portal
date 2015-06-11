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
}
