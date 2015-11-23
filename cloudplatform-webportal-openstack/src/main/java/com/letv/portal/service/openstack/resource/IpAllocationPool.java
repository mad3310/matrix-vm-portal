package com.letv.portal.service.openstack.resource;

public class IpAllocationPool {
	private String start;
	private String end;

	public IpAllocationPool() {
	}

	public IpAllocationPool(String start, String end) {
		this.start = start;
		this.end = end;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

}
