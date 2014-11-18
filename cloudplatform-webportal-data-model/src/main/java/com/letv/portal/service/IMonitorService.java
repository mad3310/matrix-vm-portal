package com.letv.portal.service;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.MonitorDetailModel;
import com.letv.portal.model.MonitorIndexModel;
import com.letv.portal.model.MonitorViewModel;

public interface IMonitorService extends IBaseService<MonitorDetailModel>{
    /**
     * Methods Name: getMonitorViewData <br>
     * Description: 获取监控数据<br>
     * @author name: wujun
     * @param MclusterId
     * @return
     */
	public MonitorViewModel getMonitorViewData(Long MclusterId,Long chartId,Integer strategy);
	
	public List<String> getMonitorXData(Integer strategy);
	
	public List<String> selectDistinct(Map map);
	
	public List<MonitorDetailModel> selectDateTime(Map map);
	
	public List<MonitorIndexModel> selectMonitorCount();
	
}
