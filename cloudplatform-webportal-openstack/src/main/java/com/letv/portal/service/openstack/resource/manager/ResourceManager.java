package com.letv.portal.service.openstack.resource.manager;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.letv.portal.service.openstack.exception.OpenStackException;
import com.letv.portal.service.openstack.resource.Region;

public interface ResourceManager extends Serializable{
	public Set<String> getRegions() throws OpenStackException;
	public Map<String, Map<String, Map<Integer, Region>>> getGroupRegions() throws OpenStackException;
    List<Region> listRegion() throws OpenStackException;
}
