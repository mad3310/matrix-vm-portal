package com.letv.portal.service.openstack.resource.manager;

import java.util.List;

import com.letv.portal.service.openstack.util.JsonUtil;
import org.codehaus.jackson.type.TypeReference;

import com.letv.portal.service.openstack.exception.OpenStackException;

public class RegionAndVmId {
	private String region;
	private String vmId;

	public RegionAndVmId() {
	}

	public RegionAndVmId(String region, String vmId) {
		this.region = region;
		this.vmId = vmId;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getVmId() {
		return vmId;
	}

	public void setVmId(String vmId) {
		this.vmId = vmId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((region == null) ? 0 : region.hashCode());
		result = prime * result + ((vmId == null) ? 0 : vmId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RegionAndVmId other = (RegionAndVmId) obj;
		if (region == null) {
			if (other.region != null)
				return false;
		} else if (!region.equals(other.region))
			return false;
		if (vmId == null) {
			if (other.vmId != null)
				return false;
		} else if (!vmId.equals(other.vmId))
			return false;
		return true;
	}

	public static List<RegionAndVmId> listFromJson(String vmIdListJson)
			throws OpenStackException {
		return JsonUtil.fromJson(vmIdListJson, new TypeReference<List<RegionAndVmId>>() {
		});
	}
}
