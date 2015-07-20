package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.monitor.MonitorViewYModel;

public interface IMonitorService extends IBaseService<MonitorDetailModel>{
	
	public List<MonitorViewYModel> getMonitorViewData(Long MclusterId,Long chartId,Integer strategy);
	public List<MonitorViewYModel> getMonitorData(String ip,Long chartId,Integer strategy,boolean isTimeAveraging,int format);
	
	public List<String> selectDistinct(Map map);
	
	public List<MonitorDetailModel> selectDateTime(Map map);
	
	public List<MonitorIndexModel> selectMonitorCount();
	public Float selectDbStorage(Long mclusterId);
	public List<Map<String,Object>> selectDbConnect(Long mclusterId);
	
	public void deleteOutDataByIndex(Map<String, Object> map);
	public List<Map<String, Object>> selectExtremeIdByMonitorDate(
			Map<String, Object> map);
	
	/**
	  * @Title: addMonitorPartition
	  * @Description: 添加分区
	  * @param map void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月17日 上午9:29:14
	  */
	public void addMonitorPartition(Map<String, Object> map);
	/**
	  * @Title: deleteMonitorPartitionThirtyDaysAgo
	  * @Description: 删除30天以前的分区
	  * @param map void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月17日 上午10:42:28
	  */
	public void deleteMonitorPartitionThirtyDaysAgo(Map<String, Object> map);
	
}
