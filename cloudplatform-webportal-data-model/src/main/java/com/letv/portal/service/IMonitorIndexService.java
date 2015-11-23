package com.letv.portal.service;

import java.util.List;

import com.letv.portal.model.MonitorIndexModel;

public interface IMonitorIndexService extends IBaseService<MonitorIndexModel>{
	/**
	 * Methods Name: selectMonitorCount <br>
	 * Description: 监控图数目<br>
	 * @author name: wujun
	 * @return
	 */
	public List<MonitorIndexModel> selectMonitorCount();
}
