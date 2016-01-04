package com.letv.lcp.openstack.util;

import java.util.HashMap;

@SuppressWarnings("serial")
public class Params extends HashMap<String, Object> {
	
	public Params p(String k, Object v) {
		this.put(k, v);
		return this;
	}
}
