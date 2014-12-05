package com.letv.portal.proxy;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.MonitorDetailModel;



public interface IDashBoardProxy{

	Map<String,Integer> selectManagerResource();

	Map<String,Integer> selectMclusterMonitor();

	Map<String,Integer> selectAppResource();

	Map<String,Float> selectDbStorage();
	
	List<Map<String,Object>> selectDbConnect();
	
}
