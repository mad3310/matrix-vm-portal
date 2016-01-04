package com.letv.lcp.openstack.model.network.impl;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jclouds.openstack.neutron.v2.domain.IP;
import org.jclouds.openstack.neutron.v2.domain.Port;

import com.letv.lcp.openstack.model.base.AbstractResource;
import com.letv.lcp.openstack.model.network.PortResource;
import com.letv.lcp.openstack.model.network.SubnetIp;


public class PortResourceImpl extends AbstractResource implements PortResource {

	private String region;
	private String regionDisplayName;
	@JsonIgnore
	private Port port;
	private List<SubnetIp> fixedIps;

	public PortResourceImpl(String region, String regionDisplayName, Port port) {
		this.region = region;
		this.regionDisplayName = regionDisplayName;
		this.port = port;
		this.fixedIps = new LinkedList<SubnetIp>();
		for (IP fixedIp : port.getFixedIps()) {
			this.fixedIps.add(new SubnetIp(fixedIp.getSubnetId(), fixedIp
					.getIpAddress()));
		}
	}

	@Override
	public String getRegion() {
		return region;
	}

	@Override
	public String getId() {
		return port.getId();
	}

	@Override
	public String getRegionDisplayName() {
		return regionDisplayName;
	}

	@Override
	public String getName() {
		return port.getName();
	}

	@Override
	public List<SubnetIp> getFixedIps() {
		return fixedIps;
	}

	@Override
	public String getStatus() {
		return port.getStatus().toString();
	}

	@Override
	public String getNetworkId() {
		return port.getNetworkId();
	}

	@Override
	public String getMacAddress() {
		return port.getMacAddress();
	}

	@Override
	public String getDeviceOwner() {
		return port.getDeviceOwner();
	}

	@Override
	public String getDeviceId() {
		return port.getDeviceId();
	}

}
