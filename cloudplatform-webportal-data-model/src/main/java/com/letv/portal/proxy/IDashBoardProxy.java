package com.letv.portal.proxy;

import java.util.List;
import java.util.Map;

import com.letv.portal.model.ContainerMonitorModel;



public interface IDashBoardProxy{

	Map<String,Integer> selectManagerResource();

	List<ContainerMonitorModel> selectMclusterMonitor();
	
}
