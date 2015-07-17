package com.letv.portal.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.letv.common.dao.IBaseDao;
import com.letv.portal.model.MonitorDetailModel;

/**
 * Program Name: IMonitorDao <br>
 * Description:  集群监控信息<br>
 * @author name: wujun <br>
 * Written Date: 2014年11月10日 <br>
 * Modified By: <br>
 * Modified Date: <br>
 */
public interface IMonitorDao extends IBaseDao<MonitorDetailModel>{
	public List<String> selectDistinct(Map map);
	public List<MonitorDetailModel> selectDateTime(Map map);
	public Float selectDbStorage(String ipAddr);
	public List<Map<String,Object>> selectDbConnect(String ipAddr);
	public void deleteOutDataByIndex(Map<String,Object> map);
	public List<Map<String,Object>> selectExtremeIdByMonitorDate(Map<String, Object> map);
	public void addMonitorPartition(Map<String,Object> map);
	public void deleteMonitorPartitionThirtyDaysAgo(Map<String,Object> map);
}
