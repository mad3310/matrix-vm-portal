package com.letv.portal.proxy;

public interface IGfsProxy{

	String getGfsPeers(String ip);
	String getGfsVolumes(String ip);
	String getVolProcessByName(String ip, String name);
	String getVolCapacityByName(String ip, String name);
}
