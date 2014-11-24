package com.letv.portal.proxy;

import java.util.Map;



public interface IDashBoardProxy{

	Map<String,Integer> selectManagerResource();

	Map<Integer,Integer> selectMclusterMonitor();
	
}
