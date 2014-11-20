package com.letv.portal.proxy;

import java.util.List;

import com.letv.portal.model.MonitorViewYModel;



public interface IMonitorProxy {
    
	
	/**
	 * Methods Name: collectMclusterServiceData <br>
	 * Description: 搜集集群服务数据任务<br>
	 * @author name: wujun
	 * @return
	 */
	public void collectMclusterServiceData();

	public List<MonitorViewYModel> getMonitorViewData(Long mclusterId, Long chartId,Integer strategy);

	
}
