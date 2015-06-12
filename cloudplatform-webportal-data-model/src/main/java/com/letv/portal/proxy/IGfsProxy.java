package com.letv.portal.proxy;

public interface IGfsProxy{

	String getGfsPeers(String ip);
	String getGfsVolumes(String ip);
	String getVolProcessByName(String ip, String name);
	String getVolCapacityByName(String ip, String name);
	String getVolConfigByName(String ip, String name);
	String getVolSplitbrainByName(String ip, String name);
	String getVolHealInfoByName(String ip, String name);
	String volStopByName(String ip, String name);
	String volStartByName(String ip, String name);
}
