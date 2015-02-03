package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.monitor.MonitorViewYModel;

public interface IMonitorService extends IBaseService<MonitorDetailModel>{
	
	public List<MonitorViewYModel> getMonitorViewData(Long MclusterId,Long chartId,Integer strategy);
	public List<MonitorViewYModel> getDbConnMonitor(String ip,Long chartId,Integer strategy);
	
	public List<String> selectDistinct(Map map);
	
	public List<MonitorDetailModel> selectDateTime(Map map);
	
	public List<MonitorIndexModel> selectMonitorCount();
	public Float selectDbStorage(Long mclusterId);
	public List<Map<String,Object>> selectDbConnect(Long mclusterId);
	
	/**Methods Name: deleteOutData <br>
	 * Description: 删除过时数据：当前时间往前一个月<br>
	 * @author name: liuhao1
	 */
	public void deleteOutData();
	
}
