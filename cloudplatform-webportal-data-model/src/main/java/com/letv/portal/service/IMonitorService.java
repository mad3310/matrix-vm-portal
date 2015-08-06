package com.letv.portal.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.letv.portal.model.ContainerModel;
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
	public void addMonitorPartition(Map<String, Object> map, Date d);
	/**
	  * @Title: deleteMonitorPartitionThirtyDaysAgo
	  * @Description: 删除30天以前的分区
	  * @param map void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月17日 上午10:42:28
	  */
	public void deleteMonitorPartitionThirtyDaysAgo(Map<String, Object> map);
	
	/**
	  * @Title: insertMysqlMonitorData
	  * @Description: 保存数据到mysql监控表(当该次收集数据为空时，把相应字段置为-1，表示数据错误)
	  * @param map void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月28日 下午2:04:23
	  */
	public void insertMysqlMonitorData(ContainerModel container, Map<String, Object> map, Date d);
	/**
	  * @Title: insertMysqlMonitorSpaceData
	  * @Description: 保存数据到mysql 表空间监控表
	  * @param container
	  * @param map
	  * @param d void   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月31日 上午10:08:02
	  */
	public void insertMysqlMonitorSpaceData(String dbName, ContainerModel container, Map<String, Object> map, Date d);
	
	/**
	  * @Title: getLatestDataFromMonitorTable
	  * @Description: 获取监控表数据库中最新记录值
	  * @param containerIp container的ip
	  * @param titles WEBPORTAL_INDEX_MONITOR表的title值
	  * @param d
	  * @return Map<String,Object>   
	  * @throws 
	  * @author lisuxiao
	  * @date 2015年7月30日 下午2:04:21
	  */
	public Map<String, Object> getLatestDataFromMonitorTables(String containerIp, String[] titles, Date d);
	
}
