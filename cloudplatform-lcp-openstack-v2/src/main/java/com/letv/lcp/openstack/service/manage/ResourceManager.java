package com.letv.lcp.openstack.service.manage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.letv.lcp.openstack.exception.OpenStackException;
import com.letv.lcp.openstack.model.region.Region;


public interface ResourceManager extends Serializable{
	public Set<String> getRegions() throws OpenStackException;
	public Map<String, Map<String, Map<Integer, Region>>> getGroupRegions() throws OpenStackException;
    List<Region> listRegion() throws OpenStackException;
}
